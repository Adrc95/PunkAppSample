package com.adrc95.punkappsample.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Beers")
data class Beer(
    @PrimaryKey()
    val id: Long,
    val name: String?,
    val tagline: String?,
    @SerialName("first_brewed")
    val firstBrewed: String?,
    val description: String?,
    @SerialName("image_url")
    val imageURL: String?,
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
    @Embedded(prefix = "volume_")
    val volume: BoilVolume?,
    @Embedded(prefix = "boilVolume_")
    @SerialName("boil_volume")
    val boilVolume: BoilVolume?,
    @Embedded
    val method: Method?,
    @Embedded
    val ingredients: Ingredients?,
    @SerialName("food_pairing")
    val foodPairings: List<String>?,
    @SerialName("brewers_tips")
    val brewersTips: String?,
    @SerialName("contributed_by")
    val contributedBy: String?
)

@Serializable
data class BoilVolume(
    val value: Double?,
    val unit: String?
)

@Serializable
data class Ingredients(
    val malt: List<Malt>?,
    val hops: List<Hop>?,
    val yeast: String?
)

@Serializable
data class Hop(
    val name: String?,
    @Embedded(prefix = "hop_")
    val amount: BoilVolume?,
    val add: String?,
    val attribute: String?
)

@Serializable
data class Malt(
    val name: String?,
    @Embedded(prefix = "malt_")
    val amount: BoilVolume?
)

@Serializable
data class Method(
    @SerialName("mash_temp")
    val mashTemp: List<MashTemp>?,
    @Embedded
    val fermentation: Fermentation?,
    val twist: String?
)

@Serializable
data class Fermentation(
    @Embedded(prefix = "fermentation_")
    val temp: BoilVolume?
)

@Serializable
data class MashTemp(
    @Embedded(prefix = "mash_temp_")
    val temp: BoilVolume?,
    val duration: Long?
)