package com.adrc95.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adrc95.core.database.dao.BeerPageCacheDao
import com.adrc95.core.database.dao.PunkDao
import com.adrc95.core.database.entity.BeerEntity
import com.adrc95.core.database.entity.BeerPageEntryEntity
import com.adrc95.core.database.entity.BeerPageCacheEntity

@TypeConverters(RequestConverters::class)
@Database(
    entities = [
        BeerEntity::class,
        BeerPageEntryEntity::class,
        BeerPageCacheEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun punkDao(): PunkDao

    abstract fun beerPageCacheDao(): BeerPageCacheDao
}
