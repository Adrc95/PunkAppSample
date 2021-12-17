package com.adrc95.punkappsample.di

import android.app.Application
import com.adrc95.punkappsample.data.dao.PunkDao
import com.adrc95.punkappsample.data.db.PunkDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): PunkDataBase = PunkDataBase.build(application)

    @Provides
    @Singleton
    fun providesPunkDao(db: PunkDataBase): PunkDao = db.punkDao()

}