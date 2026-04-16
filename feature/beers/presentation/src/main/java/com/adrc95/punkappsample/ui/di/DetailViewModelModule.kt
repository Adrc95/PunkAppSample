package com.adrc95.punkappsample.ui.di

import androidx.lifecycle.SavedStateHandle
import com.adrc95.punkappsample.ui.di.qualifier.BeerId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @BeerId
    fun provideBeerId(savedStateHandle: SavedStateHandle): Long =
        savedStateHandle.get<Long>("idBeer") ?: -1L
}
