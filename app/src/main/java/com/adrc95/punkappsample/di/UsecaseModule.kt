package com.adrc95.punkappsample.di

import com.adrc95.usecase.base.Invoker
import com.adrc95.usecase.base.UseCaseInvoker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UsecaseModule {

    @Binds
    abstract fun bindInvoker(useCaseInvoker: UseCaseInvoker): Invoker
}
