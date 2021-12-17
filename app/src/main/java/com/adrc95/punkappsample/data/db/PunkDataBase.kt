package com.adrc95.punkappsample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adrc95.punkappsample.data.Beer
import com.adrc95.punkappsample.data.dao.PunkDao

@TypeConverters(RequestConverters::class)
@Database(
    entities = [Beer::class],
    version = 1,
    exportSchema = false
)
abstract class PunkDataBase : RoomDatabase() {

    abstract fun punkDao(): PunkDao

    companion object {
        private const val DATABASE_NAME = "Punk.db"

        fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                PunkDataBase::class.java,
                DATABASE_NAME
            ).build()
    }
}
