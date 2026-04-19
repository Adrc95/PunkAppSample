package com.adrc95.punkappsample.ui.beerlist.mapper

import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.ui.beerlist.model.BeerDisplayModel

fun Beer.toDisplayModel(): BeerDisplayModel = BeerDisplayModel(
    id = id,
    name = name,
    description = description,
    imageUrl = imageURL,
)
