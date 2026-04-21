package com.adrc95.punkappsample.ui.detail.mapper

import com.adrc95.domain.builder.beer
import com.adrc95.domain.model.BoilVolume
import com.adrc95.domain.model.Hop
import com.adrc95.domain.model.Ingredients
import com.adrc95.domain.model.Malt
import com.adrc95.punkappsample.ui.detail.model.BeerInfoDisplayModel
import org.junit.Assert.assertEquals
import org.junit.Test

class BeerInfoDisplayModelMapperTest {

    @Test
    fun `given beer when mapped then returns beer info display model`() {
        // Given
        val beer = beer {
            withIngredients(
                Ingredients(
                    malt = listOf(
                        Malt(
                            name = "Extra Pale",
                            amount = BoilVolume(value = 5.3, unit = "kilograms"),
                        ),
                        Malt(
                            name = "Caramalt",
                            amount = BoilVolume(value = 0.2, unit = "kilograms"),
                        ),
                    ),
                    hops = listOf(
                        Hop(
                            name = "Ahtanum",
                            amount = BoilVolume(value = 17.5, unit = "grams"),
                            add = "start",
                            attribute = "bitter",
                        ),
                        Hop(
                            name = "Cascade",
                            amount = BoilVolume(value = 15.0, unit = "grams"),
                            add = "end",
                            attribute = "aroma",
                        ),
                    ),
                    yeast = "Wyeast 1056 - American Ale",
                )
            )
            withFoodPairings(
                listOf(
                    "Spicy chicken tikka masala",
                    "Cheesecake",
                )
            )
        }
        val expectedDisplayModel = BeerInfoDisplayModel(
            name = "Punk IPA",
            firstBrewed = "04/2007",
            description = "A beer used for domain unit tests.",
            imageUrl = "https://images.punkapi.com/v2/keg.png",
            abv = "5.6",
            maltIngredients = "<ul><li> Extra Pale</li>\n<li> Caramalt</li></ul>",
            hops = "<ul><li> Ahtanum</li>\n<li> Cascade</li></ul>",
            foodPairings = "<ul><li> Spicy chicken tikka masala</li>\n<li> Cheesecake</li></ul>",
        )

        // When
        val result = beer.toDisplayModel()

        // Then
        assertEquals(expectedDisplayModel, result)
    }
}
