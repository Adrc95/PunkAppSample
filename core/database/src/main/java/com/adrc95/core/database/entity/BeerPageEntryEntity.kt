package com.adrc95.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "beer_page_entries",
    primaryKeys = ["page", "items_per_page", "beer_id"],
    foreignKeys = [
        ForeignKey(
            entity = BeerEntity::class,
            parentColumns = ["id"],
            childColumns = ["beer_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("beer_id")]
)
data class BeerPageEntryEntity(
    val page: Int,
    @ColumnInfo(name = "items_per_page")
    val itemsPerPage: Int,
    @ColumnInfo(name = "beer_id")
    val beerId: Long,
    val position: Int
)
