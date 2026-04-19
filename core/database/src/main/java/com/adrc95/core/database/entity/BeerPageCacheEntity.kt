package com.adrc95.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "beer_page_cache",
    primaryKeys = ["page", "items_per_page"]
)
data class BeerPageCacheEntity(
    val page: Int,
    @ColumnInfo(name = "items_per_page")
    val itemsPerPage: Int,
    val updatedAt: Long
)
