package com.adrc95.domain.model

data class Ingredients(
    val malt: List<Malt>,
    val hops: List<Hop>,
    val yeast: String?
)