package com.adrc95.punkappsample.ui.detail.mapper

import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.ui.common.extension.joinToBulletList
import com.adrc95.punkappsample.ui.detail.model.BeerInfoDisplayModel

fun Beer.toDisplayModel(): BeerInfoDisplayModel {
    val maltIngredients = ingredients.malt.map { it.name }.joinToBulletList()
    val hops = ingredients.hops.map { it.name }.joinToBulletList()
    val foodPairings = foodPairings.joinToBulletList()

    return BeerInfoDisplayModel(
        name = name,
        firstBrewed = firstBrewed,
        description = description,
        imageUrl = imageURL,
        abv = abv.toString(),
        maltIngredients = maltIngredients,
        hops = hops,
        foodPairings = foodPairings,
    )
}
