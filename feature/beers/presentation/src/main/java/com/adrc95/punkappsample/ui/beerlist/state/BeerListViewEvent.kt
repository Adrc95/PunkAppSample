package com.adrc95.punkappsample.ui.beerlist.state

sealed interface BeerListViewEvent {
    data object ShowError : BeerListViewEvent
    data class NavigateToBeerDetail(val beerId: Long) : BeerListViewEvent
    data class EnableEndlessScroll(val enabled: Boolean) : BeerListViewEvent
}
