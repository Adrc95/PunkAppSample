package com.adrc95.punkappsample.di

import android.content.Context
import androidx.room.Room
import com.adrc95.punkappsample.data.dao.PunkDao
import com.adrc95.punkappsample.data.db.PunkDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): PunkDataBase =
        Room.databaseBuilder(
            context = context,
            klass = PunkDataBase::class.java,
            name = "PunkApp"
        ).build()

    @Provides
    @Singleton
    fun providesPunkDao(db: PunkDataBase): PunkDao = db.punkDao()

}