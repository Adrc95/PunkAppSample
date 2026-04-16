package com.adrc95.punkappsample.ui.detail

import com.adrc95.domain.model.Beer

sealed class DetailViewState {
    data object Loading : DetailViewState()
    data class RenderBeer(val beer: Beer) : DetailViewState()
    data object LoadDetails: DetailViewState()
}

