package com.adrc95.punkappsample.di

import com.adrc95.data.source.BeersLocalDataSource
import com.adrc95.data.source.BeersNetworkDataSource
import com.adrc95.punkappsample.data.dao.PunkDao
import com.adrc95.punkappsample.data.datasource.RemoteBeersDataSource
import com.adrc95.punkappsample.data.datasource.LocalBeersDataSource
import com.adrc95.punkappsample.data.service.APIService
import com.adrc95.punkappsample.data.service.PunkApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesBeersDataSource(api : APIService<PunkApiService>): BeersNetworkDataSource =
        RemoteBeersDataSource(api)

    @Provides
    @Singleton
    fun providesLocalBeersDataSource(punkDao: PunkDao): BeersLocalDataSource =
        LocalBeersDataSource(punkDao)
}