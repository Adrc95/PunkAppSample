package com.adrc95.domain.model

data class Beer(
    val id: Long,
    val name: String,
    val tagline: String? = null,
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
    val volume: BoilVolume? = null,
    val boilVolume: BoilVolume? = null,
    val method: Method? = null,
    val ingredients: Ingredients,
    val foodPairings: List<String>,
    val brewersTips: String? = null,
    val contributedBy: String? = null
)
