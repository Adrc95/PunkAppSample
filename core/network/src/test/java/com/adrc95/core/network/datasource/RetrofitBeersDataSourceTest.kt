package com.adrc95.core.network.datasource

import com.adrc95.core.network.service.PunkApiService
import com.adrc95.domain.exception.Error
import com.adrc95.domain.model.Beer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class RetrofitBeersDataSourceTest {
    private val server = MockWebServer()
    private lateinit var remoteDataSource: RetrofitBeersDataSource

    @Before
    fun setUp() {
        server.start()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(OkHttpClient())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
        val api = retrofit.create(PunkApiService::class.java)
        remoteDataSource = RetrofitBeersDataSource(api)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `given empty json response when getBeers then returns empty list`() = runTest {
        // Given
        server.enqueue(
            MockResponse()
                .setBody("[]")
                .setResponseCode(200)
        )

        // When
        val beers = checkNotNull(remoteDataSource.getBeers(1, 30).getOrNull())

        // Then
        Assert.assertEquals(emptyList<Beer>(), beers)
    }

    @Test
    fun `given beers page response when getBeers then returns mapped beers`() = runTest {
        // Given
        server.enqueue(
            MockResponse()
                .setBody(readResource("beers_page_1.json"))
                .setResponseCode(200)
        )

        // When
        val beers = checkNotNull(remoteDataSource.getBeers(1, 30).getOrNull())

        // Then
        Assert.assertEquals(30, beers.size)
        Assert.assertEquals(1L, beers.first().id)
        Assert.assertEquals("Punk IPA 2007 - 2010", beers.first().name)
        Assert.assertEquals("https://punkapi-alxiw.amvera.io/v3/images/001.png", beers.first().imageURL)
    }

    @Test
    fun `given server error response when getBeers then returns server error`() = runTest {
        // Given
        server.enqueue(MockResponse().setResponseCode(500))

        // When
        val result = remoteDataSource.getBeers(1, 30)

        // Then
        Assert.assertEquals(Error.Server(500), result.leftOrNull())
    }

    @Test
    fun `given getBeers call when request is executed then sends page and items per page query params`() =
        runTest {
            // Given
            server.enqueue(
                MockResponse()
                    .setBody("[]")
                    .setResponseCode(200)
            )

            // When
            remoteDataSource.getBeers(1, 30)
            val request = server.takeRequest()

            // Then
            assertEquals("/beers?page=1&per_page=30", request.path)
        }

    @Test
    fun `given beer response when getBeer then returns mapped beer`() = runTest {
        // Given
        server.enqueue(
            MockResponse()
                .setBody(readResource("beer_1.json"))
                .setResponseCode(200)
        )

        // When
        val beer = checkNotNull(remoteDataSource.getBeer(1L).getOrNull())

        // Then
        assertEquals(1L, beer.id)
        assertEquals("Punk IPA 2007 - 2010", beer.name)
        assertEquals("https://punkapi-alxiw.amvera.io/v3/images/001.png", beer.imageURL)
    }

    @Test
    fun `given getBeer call when request is executed then sends beer id in path`() = runTest {
        // Given
        server.enqueue(
            MockResponse()
                .setBody(readResource("beer_1.json"))
                .setResponseCode(200)
        )

        // When
        remoteDataSource.getBeer(1L)
        val request = server.takeRequest()

        // Then
        assertEquals("/beers/1", request.path)
    }

    @Test
    fun `given server error response when getBeer then returns server error`() = runTest {
        // Given
        server.enqueue(MockResponse().setResponseCode(500))

        // When
        val result = remoteDataSource.getBeer(1L)

        // Then
        assertEquals(Error.Server(500), result.leftOrNull())
    }

    private fun readResource(fileName: String): String =
        checkNotNull(javaClass.classLoader?.getResource(fileName)) {
            "Missing test resource: $fileName"
        }.readText()
}
