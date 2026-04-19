package com.adrc95.punkappsample.ui.detail.state

import com.adrc95.punkappsample.ui.detail.model.BeerInfoDisplayModel

data class DetailUiState(
    val isLoading: Boolean = true,
    val beer: BeerInfoDisplayModel? = null
)
