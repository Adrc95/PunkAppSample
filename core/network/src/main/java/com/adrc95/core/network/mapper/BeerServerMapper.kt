package com.adrc95.core.network.mapper

import com.adrc95.domain.model.Beer
import com.adrc95.domain.model.BoilVolume
import com.adrc95.domain.model.Fermentation
import com.adrc95.domain.model.Hop
import com.adrc95.domain.model.Ingredients as DomainIngredients
import com.adrc95.domain.model.Malt
import com.adrc95.domain.model.MashTemp
import com.adrc95.domain.model.Method
import com.adrc95.core.network.model.BeerDto
import com.adrc95.core.network.model.BoilVolumeDto
import com.adrc95.core.network.model.FermentationDto
import com.adrc95.core.network.model.HopDto
import com.adrc95.core.network.model.IngredientDto
import com.adrc95.core.network.model.MaltDto
import com.adrc95.core.network.model.MashTempDto
import com.adrc95.core.network.model.MethodDto

fun List<BeerDto>.toDomain(): List<Beer> = map { it.toDomain() }

fun BeerDto.toDomain(): Beer = Beer(
    id = id,
    name = name.orEmpty(),
    tagline = tagline,
    firstBrewed = firstBrewed.orEmpty(),
    description = description.orEmpty(),
    imageURL = image?.let { "https://punkapi-alxiw.amvera.io/v3/images/$it" }.orEmpty(),
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

private fun IngredientDto?.toDomain(): DomainIngredients = DomainIngredients(
    malt = this?.malt.orEmpty().map { it.toDomain() },
    hops = this?.hops.orEmpty().map { it.toDomain() },
    yeast = this?.yeast
)

private fun MaltDto.toDomain(): Malt = Malt(
    name = name.orEmpty(),
    amount = amount.toDomain()
)

private fun HopDto.toDomain(): Hop = Hop(
    name = name.orEmpty(),
    amount = amount.toDomain(),
    add = add.orEmpty(),
    attribute = attribute.orEmpty()
)

private fun BoilVolumeDto?.toDomain(): BoilVolume = BoilVolume(
    value = this?.value ?: 0.0,
    unit = this?.unit
)

private fun MethodDto?.toDomain(): Method? = this?.let {
    Method(
        mashTemp = it.mashTemp.orEmpty().map { mash -> mash.toDomain() },
        fermentation = it.fermentation.toDomain(),
        twist = it.twist
    )
}

private fun FermentationDto?.toDomain(): Fermentation? = this?.let {
    Fermentation(temp = it.temp.toDomain())
}

private fun MashTempDto.toDomain(): MashTemp = MashTemp(
    temp = temp.toDomain(),
    duration = duration
)
