package com.adrc95.punkappsample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "Beers")
data class BeerEntity(
    @PrimaryKey
    val id: Long,
    val name: String?,
    val tagline: String?,
    @ColumnInfo("first_brewed")
    val firstBrewed: String?,
    val description: String?,
    @ColumnInfo("image_url")
    val imageURL: String?,
    val abv: Double?,
    val ibu: Double?,
    @ColumnInfo("target_fg")
    val targetFg: Double?,
    @ColumnInfo("target_og")
    val targetOg: Double?,
    val ebc: Double?,
    val srm: Double?,
    val ph: Double?,
    @ColumnInfo("attenuation_level")
    val attenuationLevel: Double?,
    @ColumnInfo("volume")
    val volume: BoilVolumeEntity?,
    @ColumnInfo("boil_volume")
    val boilVolume: BoilVolumeEntity?,
    @ColumnInfo("method")
    val method: MethodEntity?,
    @ColumnInfo("ingredients")
    val ingredients: IngredientsEntity?,
    @ColumnInfo("food_pairing")
    val foodPairings: List<String>?,
    @ColumnInfo("brewers_tips")
    val brewersTips: String?,
    @ColumnInfo("contributed_by")
    val contributedBy: String?,
    @ColumnInfo("updated_at")
    val updatedAt: Long
)

@Serializable
data class BoilVolumeEntity(
    val value: Double?,
    val unit: String?
)

@Serializable
data class IngredientsEntity(
    val malt: List<MaltEntity>?,
    val hops: List<HopEntity>?,
    val yeast: String?
)

@Serializable
data class HopEntity(
    val name: String?,
    val amount: BoilVolumeEntity?,
    val add: String?,
    val attribute: String?
)

@Serializable
data class MaltEntity(
    val name: String?,
    val amount: BoilVolumeEntity?
)

@Serializable
data class MethodEntity(
    val mashTemp: List<MashTempEntity>?,
    val fermentation: FermentationEntity?,
    val twist: String?
)

@Serializable
data class FermentationEntity(
    val temp: BoilVolumeEntity?
)

@Serializable
data class MashTempEntity(
    val temp: BoilVolumeEntity?,
    val duration: Long?
)
