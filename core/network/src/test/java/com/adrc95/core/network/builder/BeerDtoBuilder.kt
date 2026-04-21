package com.adrc95.core.network.builder

import com.adrc95.core.network.model.BeerDto
import com.adrc95.core.network.model.BoilVolumeDto
import com.adrc95.core.network.model.FermentationDto
import com.adrc95.core.network.model.HopDto
import com.adrc95.core.network.model.IngredientDto
import com.adrc95.core.network.model.MaltDto
import com.adrc95.core.network.model.MashTempDto
import com.adrc95.core.network.model.MethodDto

class BeerDtoBuilder {
    var id: Long = 1L
    var name: String? = "Punk IPA 2007 - 2010"
    var tagline: String? = "Post Modern Classic. Spiky. Tropical. Hoppy."
    var firstBrewed: String? = "04/2007"
    var description: String? = "A beer used for network tests."
    var image: String? = "001.png"
    var abv: Double? = 6.0
    var ibu: Double? = 60.0
    var targetFg: Double? = 1010.0
    var targetOg: Double? = 1056.0
    var ebc: Double? = 17.0
    var srm: Double? = 8.5
    var ph: Double? = 4.4
    var attenuationLevel: Double? = 82.14
    var volume: BoilVolumeDto? = BoilVolumeDto(value = 20.0, unit = "litres")
    var boilVolume: BoilVolumeDto? = BoilVolumeDto(value = 25.0, unit = "litres")
    var method: MethodDto? = MethodDto(
        mashTemp = listOf(
            MashTempDto(
                temp = BoilVolumeDto(
                    value = 65.0,
                    unit = "celsius",
                ),
                duration = 75L,
            )
        ),
        fermentation = FermentationDto(
            temp = BoilVolumeDto(
                value = 19.0,
                unit = "celsius",
            )
        ),
        twist = "Orange peel",
    )
    var ingredients: IngredientDto? = IngredientDto(
        malt = listOf(
            MaltDto(
                name = "Extra Pale",
                amount = BoilVolumeDto(
                    value = 5.3,
                    unit = "kilograms",
                ),
            )
        ),
        hops = listOf(
            HopDto(
                name = "Ahtanum",
                amount = BoilVolumeDto(
                    value = 17.5,
                    unit = "grams",
                ),
                add = "start",
                attribute = "bitter",
            )
        ),
        yeast = "Wyeast 1056 - American Ale",
    )
    var foodPairings: List<String>? = listOf("Spicy chicken tikka masala")
    var brewersTips: String? = "Keep fermentation cool."
    var contributedBy: String? = "Sam Mason <samjbmason>"

    fun withId(id: Long) = apply { this.id = id }
    fun withName(name: String?) = apply { this.name = name }
    fun withTagline(tagline: String?) = apply { this.tagline = tagline }
    fun withFirstBrewed(firstBrewed: String?) = apply { this.firstBrewed = firstBrewed }
    fun withDescription(description: String?) = apply { this.description = description }
    fun withImage(image: String?) = apply { this.image = image }
    fun withAbv(abv: Double?) = apply { this.abv = abv }
    fun withIbu(ibu: Double?) = apply { this.ibu = ibu }
    fun withTargetFg(targetFg: Double?) = apply { this.targetFg = targetFg }
    fun withTargetOg(targetOg: Double?) = apply { this.targetOg = targetOg }
    fun withEbc(ebc: Double?) = apply { this.ebc = ebc }
    fun withSrm(srm: Double?) = apply { this.srm = srm }
    fun withPh(ph: Double?) = apply { this.ph = ph }
    fun withAttenuationLevel(attenuationLevel: Double?) = apply {
        this.attenuationLevel = attenuationLevel
    }
    fun withVolume(volume: BoilVolumeDto?) = apply { this.volume = volume }
    fun withBoilVolume(boilVolume: BoilVolumeDto?) = apply { this.boilVolume = boilVolume }
    fun withMethod(method: MethodDto?) = apply { this.method = method }
    fun withIngredients(ingredients: IngredientDto?) = apply { this.ingredients = ingredients }
    fun withFoodPairings(foodPairings: List<String>?) = apply { this.foodPairings = foodPairings }
    fun withBrewersTips(brewersTips: String?) = apply { this.brewersTips = brewersTips }
    fun withContributedBy(contributedBy: String?) = apply { this.contributedBy = contributedBy }

    fun build() = BeerDto(
        id = id,
        name = name,
        tagline = tagline,
        firstBrewed = firstBrewed,
        description = description,
        image = image,
        abv = abv,
        ibu = ibu,
        targetFg = targetFg,
        targetOg = targetOg,
        ebc = ebc,
        srm = srm,
        ph = ph,
        attenuationLevel = attenuationLevel,
        volume = volume,
        boilVolume = boilVolume,
        method = method,
        ingredients = ingredients,
        foodPairings = foodPairings,
        brewersTips = brewersTips,
        contributedBy = contributedBy,
    )
}

fun beerDto(block: BeerDtoBuilder.() -> Unit = {}): BeerDto =
    BeerDtoBuilder().apply(block).build()
