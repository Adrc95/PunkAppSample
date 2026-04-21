package com.adrc95.punkappsample.ui.beerlist.state

import com.adrc95.punkappsample.ui.beerlist.model.PageContentDisplayModel

data class BeerListUiState(
    val query: String = "",
    val pageContent: PageContentDisplayModel = PageContentDisplayModel(),
)
