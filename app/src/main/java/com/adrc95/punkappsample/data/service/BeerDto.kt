package com.adrc95.punkappsample.data.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BeerDto(
    val id: Long,
    val name: String?,
    val tagline: String?,
    @SerialName("first_brewed")
    val firstBrewed: String?,
    val description: String?,
    @SerialName("image")
    val image: String?,
    val abv: Double?,
    val ibu: Double?,
    @SerialName("target_fg")
    val targetFg: Double?,
    @SerialName("target_og")
    val targetOg: Double?,
    val ebc: Double?,
    val srm: Double?,
    val ph: Double?,
    @SerialName("attenuation_level")
    val attenuationLevel: Double?,
    val volume: BoilVolumeDto?,
    @SerialName("boil_volume")
    val boilVolume: BoilVolumeDto?,
    val method: MethodDto?,
    val ingredients: IngredientDto?,
    @SerialName("food_pairing")
    val foodPairings: List<String>?,
    @SerialName("brewers_tips")
    val brewersTips: String?,
    @SerialName("contributed_by")
    val contributedBy: String?
)


@Serializable
data class BoilVolumeDto(
    val value: Double?,
    val unit: String?
)

@Serializable
data class IngredientDto(
    val malt: List<MaltDto>?,
    val hops: List<HopDto>?,
    val yeast: String?
)

@Serializable
data class HopDto(
    val name: String?,
    val amount: BoilVolumeDto?,
    val add: String?,
    val attribute: String?
)

@Serializable
data class MaltDto(
    val name: String?,
    val amount: BoilVolumeDto?
)

@Serializable
data class MethodDto(
    @SerialName("mash_temp")
    val mashTemp: List<MashTempDto>?,
    val fermentation: FermentationDto?,
    val twist: String?
)

@Serializable
data class FermentationDto(
    val temp: BoilVolumeDto?
)

@Serializable
data class MashTempDto(
    val temp: BoilVolumeDto?,
    val duration: Long?
)
