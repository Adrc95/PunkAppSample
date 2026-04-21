package com.adrc95.core.database.mapper

import com.adrc95.core.database.builder.beerEntity
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
        val beerEntity = beerEntity {
            withTagline("Post Modern Classic.")
            withDescription("A hoppy beer.")
            withMethod(
                MethodEntity(
                    mashTemp = listOf(
                        MashTempEntity(
                            temp = BoilVolumeEntity(
                                value = 65.0,
                                unit = "celsius",
                            ),
                            duration = 75L,
                        )
                    ),
                    fermentation = FermentationEntity(
                        temp = BoilVolumeEntity(
                            value = 19.0,
                            unit = "celsius",
                        )
                    ),
                    twist = "Orange peel",
                )
            )
            withIngredients(
                IngredientsEntity(
                    malt = listOf(
                        MaltEntity(
                            name = "Extra Pale",
                            amount = BoilVolumeEntity(
                                value = 5.3,
                                unit = "kilograms",
                            ),
                        )
                    ),
                    hops = listOf(
                        HopEntity(
                            name = "Ahtanum",
                            amount = BoilVolumeEntity(
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
        val beerEntity = beerEntity {
            withName(null)
            withTagline(null)
            withFirstBrewed(null)
            withDescription(null)
            withImageURL(null)
            withAbv(null)
            withIbu(null)
            withTargetFg(null)
            withTargetOg(null)
            withEbc(null)
            withSrm(null)
            withPh(null)
            withAttenuationLevel(null)
            withVolume(null)
            withBoilVolume(null)
            withMethod(null)
            withIngredients(null)
            withFoodPairings(null)
            withBrewersTips(null)
            withContributedBy(null)
        }
        val expectedBeer = Beer(
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

        // When
        val beer = beerEntity.toDomain()

        // Then
        assertEquals(expectedBeer, beer)
    }
}
