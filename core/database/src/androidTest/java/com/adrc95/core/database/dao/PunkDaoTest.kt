package com.adrc95.core.database.dao

import app.cash.turbine.test
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adrc95.core.database.builder.beerEntity
import com.adrc95.core.database.db.AppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PunkDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: PunkDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).build()
        dao = database.punkDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun givenEmptyDatabase_whenGetBeers_thenEmitsEmptyList() = runTest {
        // When
        val beers = dao.getBeers(page = 1, itemsPerPage = 30).first()

        // Then
        assertTrue(beers.isEmpty())
    }

    @Test
    fun givenMissingBeerId_whenGetBeer_thenReturnsNull() = runTest {
        // When
        val beer = dao.getBeer(id = 999L)

        // Then
        assertNull(beer)
    }

    @Test
    fun givenInsertedBeer_whenGetBeer_thenReturnsStoredBeer() = runTest {
        // Given
        val expectedBeer = beerEntity { withId(1L).withName("Punk IPA") }

        // When
        dao.insert(expectedBeer)
        val storedBeer = dao.getBeer(id = 1L)

        // Then
        assertEquals(expectedBeer, storedBeer)
    }

    @Test
    fun givenBeersInDifferentPages_whenGetBeers_thenReturnsOnlyRequestedPageInPositionOrder() =
        runTest {
            // Given
            val pageOneBeer = beerEntity { withId(1L).withName("Punk IPA") }
            val pageTwoBeerFirst = beerEntity { withId(2L).withName("Elvis Juice") }
            val pageTwoBeerSecond = beerEntity { withId(3L).withName("Punk AF") }

            dao.replacePage(page = 1, itemsPerPage = 30, beers = listOf(pageOneBeer))
            dao.replacePage(
                page = 2,
                itemsPerPage = 30,
                beers = listOf(pageTwoBeerFirst, pageTwoBeerSecond),
            )

            // When
            val beers = dao.getBeers(page = 2, itemsPerPage = 30).first()

            // Then
            assertEquals(listOf(pageTwoBeerFirst, pageTwoBeerSecond), beers)
        }

    @Test
    fun givenExistingPage_whenReplacePage_thenPreviousEntriesAreReplaced() = runTest {
        // Given
        val previousBeer = beerEntity { withId(1L).withName("Punk IPA") }
        val replacementBeer = beerEntity { withId(2L).withName("Elvis Juice") }

        dao.replacePage(page = 1, itemsPerPage = 30, beers = listOf(previousBeer))

        // When
        dao.replacePage(page = 1, itemsPerPage = 30, beers = listOf(replacementBeer))
        val beers = dao.getBeers(page = 1, itemsPerPage = 30).first()

        // Then
        assertEquals(listOf(replacementBeer), beers)
    }

    @Test
    fun givenDifferentItemsPerPage_whenGetBeers_thenReturnsOnlyMatchingItemsPerPage() = runTest {
        // Given
        val beersForThirtyItems = listOf(
            beerEntity { withId(1L).withName("Punk IPA") },
        )
        val beersForTenItems = listOf(
            beerEntity { withId(2L).withName("Elvis Juice") },
        )

        dao.replacePage(page = 1, itemsPerPage = 30, beers = beersForThirtyItems)
        dao.replacePage(page = 1, itemsPerPage = 10, beers = beersForTenItems)

        // When
        val beers = dao.getBeers(page = 1, itemsPerPage = 30).first()

        // Then
        assertEquals(beersForThirtyItems, beers)
    }

    @Test
    fun givenPageBeers_whenGetBeers_thenReturnsBeersOrderedByPagePosition() = runTest {
        // Given
        val firstBeer = beerEntity { withId(2L).withName("Elvis Juice") }
        val secondBeer = beerEntity { withId(1L).withName("Punk IPA") }
        val expectedBeers = listOf(firstBeer, secondBeer)

        dao.replacePage(page = 1, itemsPerPage = 30, beers = expectedBeers)

        // When
        val beers = dao.getBeers(page = 1, itemsPerPage = 30).first()

        // Then
        assertEquals(expectedBeers, beers)
    }

    @Test
    fun givenDifferentPages_whenReplacePage_thenOtherPagesRemainUnchanged() = runTest {
        // Given
        val firstPageBeer = beerEntity { withId(1L).withName("Punk IPA") }
        val initialSecondPageBeer = beerEntity { withId(2L).withName("Elvis Juice") }
        val replacementFirstPageBeer = beerEntity { withId(3L).withName("Punk AF") }

        dao.replacePage(page = 1, itemsPerPage = 30, beers = listOf(firstPageBeer))
        dao.replacePage(page = 2, itemsPerPage = 30, beers = listOf(initialSecondPageBeer))

        // When
        dao.replacePage(page = 1, itemsPerPage = 30, beers = listOf(replacementFirstPageBeer))
        val secondPageBeers = dao.getBeers(page = 2, itemsPerPage = 30).first()

        // Then
        assertEquals(listOf(initialSecondPageBeer), secondPageBeers)
    }

    @Test
    fun givenObservedPage_whenReplacePage_thenGetBeersEmitsUpdatedPageContent() = runTest {
        // Given
        val expectedBeers = listOf(
            beerEntity { withId(1L).withName("Punk IPA") },
            beerEntity { withId(2L).withName("Elvis Juice") },
        )

        // When
        dao.getBeers(page = 1, itemsPerPage = 30).test {
            // Then
            assertTrue(awaitItem().isEmpty())

            dao.replacePage(page = 1, itemsPerPage = 30, beers = expectedBeers)

            assertEquals(expectedBeers, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
