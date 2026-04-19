package com.adrc95.punkappsample.ui.detail.model

data class BeerInfoDisplayModel(
    val name: String,
    val firstBrewed: String,
    val description: String,
    val imageUrl: String,
    val abv: String,
    val maltIngredients: String,
    val hops: String,
    val foodPairings: String,
)
