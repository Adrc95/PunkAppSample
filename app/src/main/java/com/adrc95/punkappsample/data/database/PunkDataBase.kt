package com.adrc95.punkappsample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adrc95.punkappsample.data.database.dao.PunkDao
import com.adrc95.punkappsample.data.database.entity.BeerEntity

@TypeConverters(RequestConverters::class)
@Database(
    entities = [BeerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PunkDataBase : RoomDatabase() {

    abstract fun punkDao(): PunkDao
}
