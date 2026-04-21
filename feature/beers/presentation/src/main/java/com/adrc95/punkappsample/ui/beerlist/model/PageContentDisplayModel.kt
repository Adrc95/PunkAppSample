package com.adrc95.punkappsample.ui.beerlist.model

data class PageContentDisplayModel(
    val currentPage: Int = 1,
    val isLoading: Boolean = true,
    val beers: List<BeerDisplayModel> = emptyList(),
)
