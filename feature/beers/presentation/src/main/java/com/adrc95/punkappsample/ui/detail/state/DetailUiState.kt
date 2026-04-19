package com.adrc95.punkappsample.ui.detail.state

import com.adrc95.domain.model.Beer

data class DetailUiState(
    val isLoading: Boolean = true,
    val beer: Beer? = null
)
