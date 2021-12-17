package com.adrc95.punkappsample.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.adrc95.data.repository.BeersRepository
import com.adrc95.usecase.GetBeer
import com.adrc95.usecase.base.Invoker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DetailModule {

    @Provides
    fun provideDetailViewModel(savedStateHandle: SavedStateHandle,
                               invoker: Invoker, getBeer: GetBeer): DetailViewModel
            = DetailViewModel(savedStateHandle, invoker, getBeer)

    @Provides
    fun provideGetDetailUseCase(beersRepository: BeersRepository): GetBeer
            = GetBeer(beersRepository)
}