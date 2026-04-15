package com.adrc95.punkappsample.di

import com.adrc95.data.source.BeersLocalDataSource
import com.adrc95.data.source.BeersNetworkDataSource
import com.adrc95.punkappsample.data.datasource.LocalBeersDataSource
import com.adrc95.punkappsample.data.datasource.RemoteBeersDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindBeersDataSource(remote : RemoteBeersDataSource): BeersNetworkDataSource

    @Binds
    abstract fun bindLocalBeersDataSource(local : LocalBeersDataSource): BeersLocalDataSource
}