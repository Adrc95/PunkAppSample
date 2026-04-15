package com.adrc95.punkappsample.di

import com.adrc95.data.repository.BeersRepository
import com.adrc95.data.repository.BeersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindBeersRepository(
        beersRepository: BeersRepositoryImpl
    ): BeersRepository
}