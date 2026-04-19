package com.adrc95.punkappsample.ui.beerlist.state

import com.adrc95.domain.model.Beer

sealed interface BeerListViewEvent {
    data object ShowError : BeerListViewEvent
    data class NavigateToBeerDetail(val beer: Beer) : BeerListViewEvent
    data class EnableEndlessScroll(val enabled: Boolean) : BeerListViewEvent
}
