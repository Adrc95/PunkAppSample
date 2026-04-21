package com.adrc95.core.database.builder

import com.adrc95.core.database.entity.BeerEntity
import com.adrc95.core.database.entity.BoilVolumeEntity
import com.adrc95.core.database.entity.IngredientsEntity
import com.adrc95.core.database.entity.MethodEntity

class BeerEntityBuilder {
    var id: Long = 1L
    var name: String? = "Punk IPA"
    var tagline: String? = "Post Modern Classic. Spiky. Tropical. Hoppy."
    var firstBrewed: String? = "04/2007"
    var description: String? = "A beer used for database tests."
    var imageURL: String? = "https://images.punkapi.com/v2/keg.png"
    var abv: Double? = 5.6
    var ibu: Double? = 60.0
    var targetFg: Double? = 1010.0
    var targetOg: Double? = 1056.0
    var ebc: Double? = 17.0
    var srm: Double? = 8.5
    var ph: Double? = 4.4
    var attenuationLevel: Double? = 82.14
    var volume: BoilVolumeEntity? = BoilVolumeEntity(value = 20.0, unit = "litres")
    var boilVolume: BoilVolumeEntity? = BoilVolumeEntity(value = 25.0, unit = "litres")
    var method: MethodEntity? = null
    var ingredients: IngredientsEntity? = IngredientsEntity(
        malt = emptyList(),
        hops = emptyList(),
        yeast = "Wyeast 1056 - American Ale"
    )
    var foodPairings: List<String>? = listOf("Spicy chicken tikka masala")
    var brewersTips: String? = null
    var contributedBy: String? = null

    fun withId(id: Long) = apply { this.id = id }

    fun withName(name: String?) = apply { this.name = name }

    fun withTagline(tagline: String?) = apply { this.tagline = tagline }

    fun withFirstBrewed(firstBrewed: String?) = apply { this.firstBrewed = firstBrewed }

    fun withDescription(description: String?) = apply { this.description = description }

    fun withImageURL(imageURL: String?) = apply { this.imageURL = imageURL }

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

    fun withVolume(volume: BoilVolumeEntity?) = apply { this.volume = volume }

    fun withBoilVolume(boilVolume: BoilVolumeEntity?) = apply { this.boilVolume = boilVolume }

    fun withMethod(method: MethodEntity?) = apply { this.method = method }

    fun withIngredients(ingredients: IngredientsEntity?) = apply { this.ingredients = ingredients }

    fun withFoodPairings(foodPairings: List<String>?) = apply { this.foodPairings = foodPairings }

    fun withBrewersTips(brewersTips: String?) = apply { this.brewersTips = brewersTips }

    fun withContributedBy(contributedBy: String?) = apply { this.contributedBy = contributedBy }

    fun build() = BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        firstBrewed = firstBrewed,
        description = description,
        imageURL = imageURL,
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
        contributedBy = contributedBy
    )
}

fun beerEntity(block: BeerEntityBuilder.() -> Unit = {}): BeerEntity =
    BeerEntityBuilder().apply(block).build()
