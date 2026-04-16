package com.adrc95.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adrc95.core.database.dao.PunkDao
import com.adrc95.core.database.entity.BeerEntity

@TypeConverters(RequestConverters::class)
@Database(
    entities = [BeerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun punkDao(): PunkDao
}
