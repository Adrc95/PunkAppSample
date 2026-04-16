package com.adrc95.core.database.mapper

import com.adrc95.domain.model.Beer
import com.adrc95.domain.model.BoilVolume
import com.adrc95.domain.model.Fermentation
import com.adrc95.domain.model.Hop
import com.adrc95.domain.model.Ingredients as DomainIngredients
import com.adrc95.domain.model.Malt
import com.adrc95.domain.model.MashTemp
import com.adrc95.domain.model.Method
import com.adrc95.core.database.entity.BeerEntity
import com.adrc95.core.database.entity.BoilVolumeEntity
import com.adrc95.core.database.entity.FermentationEntity
import com.adrc95.core.database.entity.HopEntity
import com.adrc95.core.database.entity.IngredientsEntity
import com.adrc95.core.database.entity.MaltEntity
import com.adrc95.core.database.entity.MashTempEntity
import com.adrc95.core.database.entity.MethodEntity

fun BeerEntity.toDomain(): Beer = Beer(
    id = id,
    name = name.orEmpty(),
    tagline = tagline,
    firstBrewed = firstBrewed.orEmpty(),
    description = description.orEmpty(),
    imageURL = imageURL.orEmpty(),
    abv = abv ?: 0.0,
    ibu = ibu ?: 0.0,
    targerFg = targetFg ?: 0.0,
    targetOg = targetOg ?: 0.0,
    ebc = ebc ?: 0.0,
    srm = srm ?: 0.0,
    ph = ph ?: 0.0,
    attenuationLevel = attenuationLevel ?: 0.0,
    volume = volume.toDomain(),
    boilVolume = boilVolume.toDomain(),
    method = method.toDomain(),
    ingredients = ingredients.toDomain(),
    foodPairings = foodPairings.orEmpty(),
    brewersTips = brewersTips,
    contributedBy = contributedBy
)

fun Beer.toEntity(): BeerEntity = BeerEntity(
    id = id,
    name = name,
    tagline = tagline,
    firstBrewed = firstBrewed,
    description = description,
    imageURL = imageURL,
    abv = abv,
    ibu = ibu,
    targetFg = targerFg,
    targetOg = targetOg,
    ebc = ebc,
    srm = srm,
    ph = ph,
    attenuationLevel = attenuationLevel,
    volume = volume?.toEntity(),
    boilVolume = boilVolume?.toEntity(),
    method = method?.toEntity(),
    ingredients = ingredients.toEntity(),
    foodPairings = foodPairings,
    brewersTips = brewersTips,
    contributedBy = contributedBy,
    updatedAt = System.currentTimeMillis()
)

private fun IngredientsEntity?.toDomain(): DomainIngredients = DomainIngredients(
    malt = this?.malt.orEmpty().map { it.toDomain() },
    hops = this?.hops.orEmpty().map { it.toDomain() },
    yeast = this?.yeast
)

private fun DomainIngredients.toEntity(): IngredientsEntity = IngredientsEntity(
    malt = malt.map { it.toEntity() },
    hops = hops.map { it.toEntity() },
    yeast = yeast
)

private fun MaltEntity.toDomain(): Malt = Malt(
    name = name.orEmpty(),
    amount = amount.toDomain()
)

private fun Malt.toEntity(): MaltEntity = MaltEntity(
    name = name,
    amount = amount.toEntity()
)

private fun HopEntity.toDomain(): Hop = Hop(
    name = name.orEmpty(),
    amount = amount.toDomain(),
    add = add.orEmpty(),
    attribute = attribute.orEmpty()
)

private fun Hop.toEntity(): HopEntity = HopEntity(
    name = name,
    amount = amount.toEntity(),
    add = add,
    attribute = attribute
)

private fun BoilVolumeEntity?.toDomain(): BoilVolume = BoilVolume(
    value = this?.value ?: 0.0,
    unit = this?.unit
)

private fun BoilVolume.toEntity(): BoilVolumeEntity = BoilVolumeEntity(
    value = value,
    unit = unit
)

private fun MethodEntity?.toDomain(): Method? = this?.let {
    Method(
        mashTemp = it.mashTemp.orEmpty().map { mash -> mash.toDomain() },
        fermentation = it.fermentation.toDomain(),
        twist = it.twist
    )
}

private fun Method.toEntity(): MethodEntity = MethodEntity(
    mashTemp = mashTemp.map { it.toEntity() },
    fermentation = fermentation?.toEntity(),
    twist = twist
)

private fun FermentationEntity?.toDomain(): Fermentation? = this?.let {
    Fermentation(temp = it.temp.toDomain())
}

private fun Fermentation.toEntity(): FermentationEntity = FermentationEntity(
    temp = temp?.toEntity()
)

private fun MashTempEntity.toDomain(): MashTemp = MashTemp(
    temp = temp.toDomain(),
    duration = duration
)

private fun MashTemp.toEntity(): MashTempEntity = MashTempEntity(
    temp = temp?.toEntity(),
    duration = duration
)
