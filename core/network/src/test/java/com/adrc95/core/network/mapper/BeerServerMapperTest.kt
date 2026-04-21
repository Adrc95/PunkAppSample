package com.adrc95.core.network.mapper

import com.adrc95.core.network.builder.beerDto
import com.adrc95.domain.builder.beer
import com.adrc95.domain.model.BoilVolume
import com.adrc95.domain.model.Fermentation
import com.adrc95.domain.model.Hop
import com.adrc95.domain.model.Ingredients
import com.adrc95.domain.model.Malt
import com.adrc95.domain.model.MashTemp
import com.adrc95.domain.model.Method
import org.junit.Assert.assertEquals
import org.junit.Test

class BeerServerMapperTest {

    @Test
    fun `given beer dto when mapped to domain then returns beer`() {
        // Given
        val beerDto = beerDto {
            withDescription("A hoppy beer.")
        }
        val expectedBeer = beer {
            withId(1L)
            withName("Punk IPA 2007 - 2010")
            withTagline("Post Modern Classic. Spiky. Tropical. Hoppy.")
            withFirstBrewed("04/2007")
            withDescription("A hoppy beer.")
            withImageURL("https://punkapi-alxiw.amvera.io/v3/images/001.png")
            withAbv(6.0)
            withIbu(60.0)
            withTargerFg(1010.0)
            withTargetOg(1056.0)
            withEbc(17.0)
            withSrm(8.5)
            withPh(4.4)
            withAttenuationLevel(82.14)
            withVolume(
                BoilVolume(
                    value = 20.0,
                    unit = "litres",
                )
            )
            withBoilVolume(
                BoilVolume(
                    value = 25.0,
                    unit = "litres",
                )
            )
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
            withFoodPairings(listOf("Spicy chicken tikka masala"))
            withBrewersTips("Keep fermentation cool.")
            withContributedBy("Sam Mason <samjbmason>")
        }

        // When
        val beer = beerDto.toDomain()

        // Then
        assertEquals(expectedBeer, beer)
    }

    @Test
    fun `given beer dto with null values when mapped to domain then returns default values`() {
        // Given
        val beerDto = beerDto {
            withName(null)
            withTagline(null)
            withFirstBrewed(null)
            withDescription(null)
            withImage(null)
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
        val expectedBeer = beer {
            withId(1L)
            withName("")
            withTagline(null)
            withFirstBrewed("")
            withDescription("")
            withImageURL("")
            withAbv(0.0)
            withIbu(0.0)
            withTargerFg(0.0)
            withTargetOg(0.0)
            withEbc(0.0)
            withSrm(0.0)
            withPh(0.0)
            withAttenuationLevel(0.0)
            withVolume(BoilVolume(value = 0.0, unit = null))
            withBoilVolume(BoilVolume(value = 0.0, unit = null))
            withMethod(null)
            withIngredients(
                Ingredients(
                    malt = emptyList(),
                    hops = emptyList(),
                    yeast = null,
                )
            )
            withFoodPairings(emptyList())
            withBrewersTips(null)
            withContributedBy(null)
        }

        // When
        val beer = beerDto.toDomain()

        // Then
        assertEquals(expectedBeer, beer)
    }
}
