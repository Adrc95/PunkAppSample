package com.adrc95.punkappsample.di

import com.adrc95.data.source.BeersNetworkDataSource
import com.adrc95.data.repository.BeersRepository
import com.adrc95.data.repository.BeersRepositoryImpl
import com.adrc95.data.source.BeersLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesBeersRepository(networkDataSource: BeersNetworkDataSource, localDataSource: BeersLocalDataSource): BeersRepository =
        BeersRepositoryImpl(networkDataSource, localDataSource)
}