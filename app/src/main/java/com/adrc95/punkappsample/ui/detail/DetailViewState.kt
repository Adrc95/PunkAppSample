package com.adrc95.punkappsample.ui.detail

import com.adrc95.domain.model.Beer

sealed class DetailViewState {
    object Loading : DetailViewState()
    class RenderBeer(val beer: Beer) : DetailViewState()
    object LoadDetails: DetailViewState()
}

