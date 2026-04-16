package com.adrc95.punkappsample.di

import com.adrc95.core.database.datasource.RoomBeersDataSource
import com.adrc95.core.network.datasource.RetrofitBeersDataSource
import com.adrc95.feature.beers.data.datasource.BeersLocalDataSource
import com.adrc95.feature.beers.data.datasource.BeersNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindBeersDataSource(remote: RetrofitBeersDataSource): BeersNetworkDataSource

    @Binds
    abstract fun bindLocalBeersDataSource(local: RoomBeersDataSource): BeersLocalDataSource
}
