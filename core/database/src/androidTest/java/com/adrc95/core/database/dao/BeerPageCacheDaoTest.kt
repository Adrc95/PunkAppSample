package com.adrc95.core.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adrc95.core.database.db.AppDatabase
import com.adrc95.core.database.entity.BeerPageCacheEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeerPageCacheDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: BeerPageCacheDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).build()
        dao = database.beerPageCacheDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun givenMissingPageCache_whenGetUpdatedAt_thenReturnsNull() = runTest {
        // When
        val updatedAt = dao.getUpdatedAt(page = 1, itemsPerPage = 30)

        // Then
        assertNull(updatedAt)
    }

    @Test
    fun givenStoredPageCache_whenGetUpdatedAt_thenReturnsStoredTimestamp() = runTest {
        // Given
        val cache = BeerPageCacheEntity(
            page = 1,
            itemsPerPage = 30,
            updatedAt = 123456789L,
        )

        // When
        dao.upsert(cache)
        val updatedAt = dao.getUpdatedAt(page = 1, itemsPerPage = 30)

        // Then
        assertEquals(123456789L, updatedAt)
    }

    @Test
    fun givenExistingPageCache_whenUpsert_thenReplacesStoredTimestamp() = runTest {
        // Given
        dao.upsert(
            BeerPageCacheEntity(
                page = 1,
                itemsPerPage = 30,
                updatedAt = 1000L,
            )
        )

        // When
        dao.upsert(
            BeerPageCacheEntity(
                page = 1,
                itemsPerPage = 30,
                updatedAt = 2000L,
            )
        )
        val updatedAt = dao.getUpdatedAt(page = 1, itemsPerPage = 30)

        // Then
        assertEquals(2000L, updatedAt)
    }

    @Test
    fun givenDifferentItemsPerPage_whenGetUpdatedAt_thenReturnsOnlyMatchingEntry() = runTest {
        // Given
        dao.upsert(
            BeerPageCacheEntity(
                page = 1,
                itemsPerPage = 10,
                updatedAt = 1000L,
            )
        )
        dao.upsert(
            BeerPageCacheEntity(
                page = 1,
                itemsPerPage = 30,
                updatedAt = 2000L,
            )
        )

        // When
        val updatedAt = dao.getUpdatedAt(page = 1, itemsPerPage = 30)

        // Then
        assertEquals(2000L, updatedAt)
    }

    @Test
    fun givenDifferentPages_whenUpsert_thenOtherPagesRemainUnchanged() = runTest {
        // Given
        dao.upsert(
            BeerPageCacheEntity(
                page = 1,
                itemsPerPage = 30,
                updatedAt = 1000L,
            )
        )
        dao.upsert(
            BeerPageCacheEntity(
                page = 2,
                itemsPerPage = 30,
                updatedAt = 2000L,
            )
        )

        // When
        dao.upsert(
            BeerPageCacheEntity(
                page = 1,
                itemsPerPage = 30,
                updatedAt = 3000L,
            )
        )
        val updatedAt = dao.getUpdatedAt(page = 2, itemsPerPage = 30)

        // Then
        assertEquals(2000L, updatedAt)
    }
}
