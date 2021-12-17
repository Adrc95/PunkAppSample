package com.adrc95.punkappsample.ui.beerlist

import com.adrc95.data.repository.BeersRepository
import com.adrc95.usecase.GetBeers
import com.adrc95.usecase.base.Invoker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object BeerListModule {

    @Provides
    fun provideBeerListViewModel(invoker: Invoker, getBeers: GetBeers): BeerListViewModel
            = BeerListViewModel(invoker, getBeers)

    @Provides
    fun provideGetBeersUseCase(beersRepository: BeersRepository): GetBeers
            = GetBeers(beersRepository)
}