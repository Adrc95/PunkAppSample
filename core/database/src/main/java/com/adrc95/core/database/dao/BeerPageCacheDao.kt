package com.adrc95.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adrc95.core.database.entity.BeerPageCacheEntity

@Dao
interface BeerPageCacheDao {

    @Query(
        """
        SELECT updatedAt FROM beer_page_cache
        WHERE page = :page AND items_per_page = :itemsPerPage
        LIMIT 1
        """
    )
    suspend fun getUpdatedAt(page: Int, itemsPerPage: Int): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cache: BeerPageCacheEntity)
}
