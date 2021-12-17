package com.adrc95.punkappsample.data.mapper

import com.adrc95.domain.model.*


fun List<com.adrc95.punkappsample.data.Beer>.toDomain(): List<Beer> = this.map { it.toDomain() }

fun com.adrc95.punkappsample.data.Beer.toDomain(): Beer = Beer(
     this.id,
    this.name ?: "",
    this.firstBrewed ?: "",
    this.description ?: "",
    this.imageURL ?: "",
    this.abv ?: 0.0,
    this.ibu ?: 0.0,
    this.targetFg ?: 0.0,
    this.targetOg ?: 0.0,
    this.ebc ?: 0.0,
    this.srm ?: 0.0,
    this.ph ?: 0.0,
    this.attenuationLevel ?: 0.0,
    this.ingredients!!.toDomain(),
    this.foodPairings ?: arrayListOf()
)

fun com.adrc95.punkappsample.data.Ingredients.toDomain(): Ingredients = Ingredients(
    this.malt!!.toDomain(),
    this.hops!!.toDomain(),
    this.yeast ?: ""
)

@JvmName("toDomain Hop")
fun List<com.adrc95.punkappsample.data.Hop>.toDomain(): List<Hop> = this.map { it.toDomain() }

@JvmName("toDomain Malt")
fun List<com.adrc95.punkappsample.data.Malt>.toDomain(): List<Malt> = this.map { it.toDomain() }

fun com.adrc95.punkappsample.data.Malt.toDomain(): Malt = Malt(
    this.name ?: "",
    this.amount!!.toDomain()
)

fun com.adrc95.punkappsample.data.Hop.toDomain(): Hop = Hop(
    this.name ?: "",
    this.amount!!.toDomain(),
    this.add ?: "",
    this.attribute ?: ""
)

fun com.adrc95.punkappsample.data.BoilVolume.toDomain(): BoilVolume = BoilVolume(
    this.value ?: 0.0,
    this.unit ?: ""
)

fun Beer.toEntity(): com.adrc95.punkappsample.data.Beer = com.adrc95.punkappsample.data.Beer(
    this.id,
    this.name,
    null,
    this.firstBrewed,
    this.description ,
    this.imageURL,
    this.abv,
    this.ibu,
    this.targerFg,
    this.targetOg,
    this.ebc,
    this.srm,
    this.ph,
    this.attenuationLevel,
    null,
    null,
    null,
    this.ingredients.toEntity(),
    this.foodPairings,
    null,
    null
)

fun Ingredients.toEntity(): com.adrc95.punkappsample.data.Ingredients = com.adrc95.punkappsample.data.Ingredients(
    this.malt.toEntity(),
    this.hops.toEntity(),
    this.yeast
)

@JvmName("toEntity Hop")
fun List<Hop>.toEntity(): List< com.adrc95.punkappsample.data.Hop> = this.map { it.toEntity() }

@JvmName("toEntity Malt")
fun List<Malt>.toEntity(): List< com.adrc95.punkappsample.data.Malt> = this.map { it.toEntity() }

fun Malt.toEntity(): com.adrc95.punkappsample.data.Malt = com.adrc95.punkappsample.data.Malt(
    this.name,
    this.amount.toEntity()
)

fun Hop.toEntity(): com.adrc95.punkappsample.data.Hop = com.adrc95.punkappsample.data.Hop(
    this.name,
    this.amount.toEntity(),
    this.add,
    this.attribute
)

fun BoilVolume.toEntity(): com.adrc95.punkappsample.data.BoilVolume = com.adrc95.punkappsample.data.BoilVolume(
    this.value,
    this.unit
)
