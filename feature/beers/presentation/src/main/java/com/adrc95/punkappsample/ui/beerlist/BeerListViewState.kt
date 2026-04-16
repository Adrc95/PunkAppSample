package com.adrc95.punkappsample.ui.beerlist

import com.adrc95.domain.model.Beer

sealed class BeerListViewState {
    data object Loading : BeerListViewState()
    data class ShowBeers(val beers: List<Beer>, val refresh: Boolean) : BeerListViewState()
    data class ChangeSearchText(val query: String) : BeerListViewState()
    data object LoadBeers : BeerListViewState()
}
