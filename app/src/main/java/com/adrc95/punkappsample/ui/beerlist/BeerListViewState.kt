package com.adrc95.punkappsample.ui.beerlist

import com.adrc95.domain.model.Beer

sealed class BeerListViewState {
        object Loading : BeerListViewState()
        class ShowBeers(val beers: List<Beer>, val refresh: Boolean) : BeerListViewState()
        class ChangeSearchText(val query: String) : BeerListViewState()
        object LoadBeers : BeerListViewState()
    }
