package com.adrc95.punkappsample.ui.beerlist.mapper

import com.adrc95.domain.builder.beer
import com.adrc95.punkappsample.ui.beerlist.model.BeerDisplayModel
import org.junit.Assert.assertEquals
import org.junit.Test

class BeerDisplayModelMapperTest {

    @Test
    fun `given beer when mapped then returns beer display model`() {
        // Given
        val beer = beer()
        val expectedDisplayModel = BeerDisplayModel(
            id = 1L,
            name = "Punk IPA",
            description = "A beer used for domain unit tests.",
            imageUrl = "https://images.punkapi.com/v2/keg.png",
        )

        // When
        val displayModel = beer.toDisplayModel()

        // Then
        assertEquals(expectedDisplayModel, displayModel)
    }
}
