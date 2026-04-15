package com.adrc95.domain.model

data class Method(
    val mashTemp: List<MashTemp> = emptyList(),
    val fermentation: Fermentation? = null,
    val twist: String? = null
)
