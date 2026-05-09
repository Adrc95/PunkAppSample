package com.adrc95.core.database.mapper

import com.adrc95.core.database.entity.BeerEntity
import com.adrc95.core.database.entity.BoilVolumeEntity
import com.adrc95.core.database.entity.FermentationEntity
import com.adrc95.core.database.entity.HopEntity
import com.adrc95.core.database.entity.IngredientsEntity
import com.adrc95.core.database.entity.MaltEntity
import com.adrc95.core.database.entity.MashTempEntity
import com.adrc95.core.database.entity.MethodEntity
import com.adrc95.domain.builder.beer
import com.adrc95.domain.model.Beer
import com.adrc95.domain.model.BoilVolume
import com.adrc95.domain.model.Fermentation
import com.adrc95.domain.model.Hop
import com.adrc95.domain.model.Ingredients
import com.adrc95.domain.model.Malt
import com.adrc95.domain.model.MashTemp
import com.adrc95.domain.model.Method
import org.junit.Assert.assertEquals
import org.junit.Test

class BeerDatabaseMapperTest {

    @Test
    fun `given beer entity when mapped to domain then returns beer`() {
        // Given
        val beerEntity = createBeerEntity()
        val expectedBeer = beer {
            withTagline("Post Modern Classic.")
            withDescription("A hoppy beer.")
            withMethod(
                Method(
                    mashTemp = listOf(
                        MashTemp(
                            temp = BoilVolume(
                                value = 65.0,
                                unit = "celsius",
                            ),
                            duration = 75L,
                        )
                    ),
                    fermentation = Fermentation(
                        temp = BoilVolume(
                            value = 19.0,
                            unit = "celsius",
                        )
                    ),
                    twist = "Orange peel",
                )
            )
            withIngredients(
                Ingredients(
                    malt = listOf(
                        Malt(
                            name = "Extra Pale",
                            amount = BoilVolume(
                                value = 5.3,
                                unit = "kilograms",
                            ),
                        )
                    ),
                    hops = listOf(
                        Hop(
                            name = "Ahtanum",
                            amount = BoilVolume(
                                value = 17.5,
                                unit = "grams",
                            ),
                            add = "start",
                            attribute = "bitter",
                        )
                    ),
                    yeast = "Wyeast 1056 - American Ale",
                )
            )
            withBrewersTips("Keep fermentation cool.")
            withContributedBy("Sam Mason <samjbmason>")
        }

        // When
        val beer = beerEntity.toDomain()

        // Then
        assertEquals(expectedBeer, beer)
    }

    @Test
    fun `given beer entity with null values when mapped to domain then returns default values`() {
        // Given
        val beerEntity = createNullBeerEntity()
        val expectedBeer = expectedBeerWithDefaultValues()

        // When
        val beer = beerEntity.toDomain()

        // Then
        assertEquals(expectedBeer, beer)
    }

    private fun createBeerEntity(): BeerEntity {
        return BeerEntity(
            id = 1L,
            name = "Punk IPA",
            tagline = "Post Modern Classic.",
            firstBrewed = "04/2007",
            description = "A hoppy beer.",
            imageURL = "https://images.punkapi.com/v2/keg.png",
            abv = 5.6,
            ibu = 60.0,
            targetFg = 1010.0,
            targetOg = 1056.0,
            ebc = 17.0,
            srm = 8.5,
            ph = 4.4,
            attenuationLevel = 82.14,
            volume = boilVolumeEntity(value = 20.0, unit = "litres"),
            boilVolume = boilVolumeEntity(value = 25.0, unit = "litres"),
            method = methodEntity(),
            ingredients = ingredientsEntity(),
            foodPairings = listOf("Spicy chicken tikka masala"),
            brewersTips = "Keep fermentation cool.",
            contributedBy = "Sam Mason <samjbmason>",
        )
    }

    private fun createNullBeerEntity(): BeerEntity {
        return BeerEntity(
            id = 1L,
            name = null,
            tagline = null,
            firstBrewed = null,
            description = null,
            imageURL = null,
            abv = null,
            ibu = null,
            targetFg = null,
            targetOg = null,
            ebc = null,
            srm = null,
            ph = null,
            attenuationLevel = null,
            volume = null,
            boilVolume = null,
            method = null,
            ingredients = null,
            foodPairings = null,
            brewersTips = null,
            contributedBy = null,
        )
    }

    private fun expectedBeerWithDefaultValues(): Beer {
        return Beer(
            id = 1L,
            name = "",
            tagline = null,
            firstBrewed = "",
            description = "",
            imageURL = "",
            abv = 0.0,
            ibu = 0.0,
            targerFg = 0.0,
            targetOg = 0.0,
            ebc = 0.0,
            srm = 0.0,
            ph = 0.0,
            attenuationLevel = 0.0,
            volume = BoilVolume(
                value = 0.0,
                unit = null,
            ),
            boilVolume = BoilVolume(
                value = 0.0,
                unit = null,
            ),
            method = null,
            ingredients = Ingredients(
                malt = emptyList(),
                hops = emptyList(),
                yeast = null,
            ),
            foodPairings = emptyList(),
            brewersTips = null,
            contributedBy = null,
        )
    }

    private fun boilVolumeEntity(value: Double, unit: String): BoilVolumeEntity {
        return BoilVolumeEntity(
            value = value,
            unit = unit,
        )
    }

    private fun methodEntity(): MethodEntity {
        return MethodEntity(
            mashTemp = listOf(
                MashTempEntity(
                    temp = boilVolumeEntity(value = 65.0, unit = "celsius"),
                    duration = 75L,
                )
            ),
            fermentation = FermentationEntity(
                temp = boilVolumeEntity(value = 19.0, unit = "celsius"),
            ),
            twist = "Orange peel",
        )
    }

    private fun ingredientsEntity(): IngredientsEntity {
        return IngredientsEntity(
            malt = listOf(
                MaltEntity(
                    name = "Extra Pale",
                    amount = boilVolumeEntity(value = 5.3, unit = "kilograms"),
                )
            ),
            hops = listOf(
                HopEntity(
                    name = "Ahtanum",
                    amount = boilVolumeEntity(value = 17.5, unit = "grams"),
                    add = "start",
                    attribute = "bitter",
                )
            ),
            yeast = "Wyeast 1056 - American Ale",
        )
    }
}
