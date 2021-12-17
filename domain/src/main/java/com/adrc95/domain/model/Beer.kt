package com.adrc95.domain.model

data class Beer(
    val id: Long,
    val name: String,
    val firstBrewed: String,
    val description: String,
    val imageURL: String,
    val abv: Double,
    val ibu: Double,
    val targerFg: Double,
    val targetOg: Double,
    val ebc: Double,
    val srm: Double,
    val ph: Double,
    val attenuationLevel: Double,
    val ingredients: Ingredients,
    val foodPairings: List<String>
)
