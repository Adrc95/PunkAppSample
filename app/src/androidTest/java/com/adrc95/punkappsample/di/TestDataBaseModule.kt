package com.adrc95.punkappsample.di

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.adrc95.core.database.dao.BeerPageCacheDao
import com.adrc95.core.database.dao.PunkDao
import com.adrc95.core.database.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataBaseModule::class]
)
object TestDataBaseModule {
    @Provides
    @Singleton
    fun providesDatabase(): AppDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java,
    ).build()

    @Provides
    @Singleton
    fun providesPunkDao(db: AppDatabase): PunkDao = db.punkDao()

    @Provides
    @Singleton
    fun providesBeerPageCacheDao(db: AppDatabase): BeerPageCacheDao = db.beerPageCacheDao()
}
