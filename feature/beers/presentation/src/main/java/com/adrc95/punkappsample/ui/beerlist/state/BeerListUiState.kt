package com.adrc95.punkappsample.ui.beerlist.state

import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.ui.beerlist.model.BeerDisplayModel

data class BeerListUiState(
    val currentPage: Int = 1,
    val isLoading: Boolean = true,
    val query: String = "",
    val allBeers: List<Beer> = emptyList(),
    val beers: List<BeerDisplayModel> = emptyList()
)
