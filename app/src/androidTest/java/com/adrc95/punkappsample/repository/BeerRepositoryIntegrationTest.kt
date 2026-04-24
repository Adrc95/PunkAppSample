package com.adrc95.punkappsample.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import arrow.core.Either
import com.adrc95.core.database.db.AppDatabase
import com.adrc95.core.database.entity.BeerPageCacheEntity
import com.adrc95.domain.BeerPagingConfig
import com.adrc95.domain.exception.Error
import com.adrc95.domain.repository.BeerRepository
import com.adrc95.feature.beers.data.CACHE_MAX_AGE_MILLIS
import com.adrc95.punkappsample.mockwebserver.MockWebServerUrlHolder
import com.adrc95.punkappsample.mockwebserver.rules.MockWebServerRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BeerRepositoryIntegrationTest {

    @get:Rule(order = 0)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: BeerRepository

    @Inject
    lateinit var database: AppDatabase

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        MockWebServerUrlHolder.baseUrl = "http://localhost:8080/"
    }

    @Test
    fun refreshBeers_forceTrue_fetchesRemotePage_andPersistsItLocally() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(readJsonAsset("beers_page_1.json"))
        )

        // When
        val error = repository.refreshBeers(page = 1, force = true)
        val beers = repository.getBeers(page = 1).first()
        val request = mockWebServerRule.server.takeRequest()

        // Then
        assertNull(error)
        assertEquals("/beers?page=1&per_page=${BeerPagingConfig.ITEMS_PER_PAGE}", request.path)
        assertEquals(BeerPagingConfig.ITEMS_PER_PAGE, beers.size)
        assertEquals(1L, beers.first().id)
        assertEquals("Punk IPA 2007 - 2010", beers.first().name)
        assertEquals("https://punkapi-alxiw.amvera.io/v3/images/001.png", beers.first().imageURL)
        assertNotNull(database.beerPageCacheDao().getUpdatedAt(1, BeerPagingConfig.ITEMS_PER_PAGE))
    }

    @Test
    fun refreshBeers_whenRemoteFails_andLocalPageExists_keepsLocalData() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(readJsonAsset("beers_page_1.json"))
        )
        repository.refreshBeers(page = 1, force = true)
        val cachedBeforeFailure = repository.getBeers(page = 1).first()

        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{}")
        )

        // When
        val error = repository.refreshBeers(page = 1, force = true)
        val cachedAfterFailure = repository.getBeers(page = 1).first()

        // Then
        assertNull(error)
        assertEquals(2, mockWebServerRule.server.requestCount)
        assertEquals(cachedBeforeFailure, cachedAfterFailure)
    }

    @Test
    fun getBeer_whenStoredLocally_returnsItWithoutCallingRemote() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(readJsonAsset("beers_page_1.json"))
        )
        repository.refreshBeers(page = 1, force = true)
        val requestCountBeforeLookup = mockWebServerRule.server.requestCount

        // When
        val result = repository.getBeer(1L)

        // Then
        assertEquals(requestCountBeforeLookup, mockWebServerRule.server.requestCount)
        assertEquals(Either.Right("Punk IPA 2007 - 2010"), result.map { it.name })
    }

    @Test
    fun getBeer_whenNotStoredLocally_fetchesRemoteBeer_andPersistsIt() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(readJsonAsset("beer_1.json"))
        )

        // When
        val result = repository.getBeer(1L)
        val request = mockWebServerRule.server.takeRequest()
        val storedBeer = repository.getBeer(1L)

        // Then
        assertEquals("/beers/1", request.path)
        assertEquals(Either.Right("Punk IPA 2007 - 2010"), result.map { it.name })
        assertEquals(1, mockWebServerRule.server.requestCount)
        assertEquals(Either.Right("Punk IPA 2007 - 2010"), storedBeer.map { it.name })
    }

    @Test
    fun getBeer_whenRemoteFails_andBeerIsNotStoredLocally_returnsApiError() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{}")
        )

        // When
        val result = repository.getBeer(1L)
        val request = mockWebServerRule.server.takeRequest()

        // Then
        assertEquals("/beers/1", request.path)
        assertTrue(result is Either.Left)
        assertEquals(Error.Server(500), (result as Either.Left).value)
    }

    @Test
    fun refreshBeers_whenForceFalse_andCacheIsFresh_doesNotCallRemote() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(readJsonAsset("beers_page_1.json"))
        )
        repository.refreshBeers(page = 1, force = true)
        val requestCountAfterSeeding = mockWebServerRule.server.requestCount

        // When
        val error = repository.refreshBeers(page = 1, force = false)
        val beers = repository.getBeers(page = 1).first()

        // Then
        assertNull(error)
        assertEquals(requestCountAfterSeeding, mockWebServerRule.server.requestCount)
        assertEquals(BeerPagingConfig.ITEMS_PER_PAGE, beers.size)
        assertEquals("Punk IPA 2007 - 2010", beers.first().name)
    }

    @Test
    fun refreshBeers_whenNoLocalData_andRemoteFails_returnsApiError() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{}")
        )

        // When
        val result = repository.refreshBeers(page = 1, force = true)
        val request = mockWebServerRule.server.takeRequest()
        val beers = repository.getBeers(page = 1).first()

        // Then
        assertEquals("/beers?page=1&per_page=${BeerPagingConfig.ITEMS_PER_PAGE}", request.path)
        assertEquals(Error.Server(500), result)
        assertTrue(beers.isEmpty())
    }

    @Test
    fun getBeers_emitsLocalDataAfterRefresh() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(readJsonAsset("beers_page_1.json"))
        )
        repository.refreshBeers(page = 1, force = true)

        // When
        val beers = repository.getBeers(page = 1).first()

        // Then
        assertEquals(BeerPagingConfig.ITEMS_PER_PAGE, beers.size)
        assertEquals("Punk IPA 2007 - 2010", beers.first().name)
    }

    @Test
    fun refreshBeers_whenCacheIsExpired_andForceFalse_callsRemoteAgain() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(readJsonAsset("beers_page_1.json"))
        )
        repository.refreshBeers(page = 1, force = true)
        database.beerPageCacheDao().upsert(
            BeerPageCacheEntity(
                page = 1,
                itemsPerPage = BeerPagingConfig.ITEMS_PER_PAGE,
                updatedAt = System.currentTimeMillis() - CACHE_MAX_AGE_MILLIS - 1
            )
        )
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(readJsonAsset("beers_page_1.json"))
        )

        // When
        val error = repository.refreshBeers(page = 1, force = false)

        // Then
        assertNull(error)
        assertEquals(2, mockWebServerRule.server.requestCount)
    }

    @Test
    fun refreshBeers_page2_fetchesAndPersistsSecondPage() = runTest {
        // Given
        mockWebServerRule.server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(readJsonAsset("beers_page_2.json"))
        )

        // When
        val error = repository.refreshBeers(page = 2, force = true)
        val beers = repository.getBeers(page = 2).first()
        val request = mockWebServerRule.server.takeRequest()

        // Then
        assertNull(error)
        assertEquals("/beers?page=2&per_page=${BeerPagingConfig.ITEMS_PER_PAGE}", request.path)
        assertEquals(BeerPagingConfig.ITEMS_PER_PAGE, beers.size)
        assertNotNull(database.beerPageCacheDao().getUpdatedAt(2, BeerPagingConfig.ITEMS_PER_PAGE))
    }

    private fun readJsonAsset(fileName: String): String =
        InstrumentationRegistry.getInstrumentation()
            .context
            .assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }
}
