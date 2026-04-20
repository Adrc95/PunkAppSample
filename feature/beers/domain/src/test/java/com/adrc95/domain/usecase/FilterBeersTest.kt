package com.adrc95.domain.usecase

import com.adrc95.domain.builder.beer
import com.adrc95.domain.model.Beer
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterBeersTest {

    @Test
    fun `given empty query when invoked then returns all beers`() {
        //Given
        val firstBeer = beer { withId(1L).withName("Punk IPA") }
        val secondBeer = beer { withId(2L).withName("Elvis Juice") }
        val beersToFilter = listOf(
            firstBeer,
            secondBeer
        )
        val useCase = FilterBeers()

        //When
        val result = useCase(beers = beersToFilter, query = "")

        //Then
        assertEquals(beersToFilter, result)
    }

    @Test
    fun `given blank query when invoked then returns all beers`() {
        //Given
        val firstBeer = beer { withId(1L).withName("Punk IPA") }
        val secondBeer = beer { withId(2L).withName("Elvis Juice") }
        val beersToFilter = listOf(
            firstBeer,
            secondBeer
        )
        val useCase = FilterBeers()

        //When
        val result = useCase(beers = beersToFilter, query = "   ")

        //Then
        assertEquals(beersToFilter, result)
    }

    @Test
    fun `given matching query when invoked then returns matching beers`() {
        //Given
        val firstMatchingBeer = beer { withId(1L).withName("Punk IPA") }
        val secondMatchingBeer = beer { withId(3L).withName("Punk AF") }
        val nonMatchingBeer = beer { withId(2L).withName("Elvis Juice") }
        val beersToFilter = listOf(
            firstMatchingBeer,
            nonMatchingBeer,
            secondMatchingBeer
        )
        val expectedBeers = listOf(
            firstMatchingBeer,
            secondMatchingBeer
        )
        val useCase = FilterBeers()

        //When
        val result = useCase(beers = beersToFilter, query = "Punk")

        //Then
        assertEquals(expectedBeers, result)
    }

    @Test
    fun `given query with different case when invoked then returns matching beers`() {
        //Given
        val matchingBeer = beer { withId(1L).withName("Punk IPA") }
        val nonMatchingBeer = beer { withId(2L).withName("Elvis Juice") }
        val beersToFilter = listOf(
            matchingBeer,
            nonMatchingBeer
        )
        val expectedBeers = listOf(matchingBeer)
        val useCase = FilterBeers()

        //When
        val result = useCase(beers = beersToFilter, query = "punk")

        //Then
        assertEquals(expectedBeers, result)
    }

    @Test
    fun `given query with surrounding spaces when invoked then returns matching beers`() {
        //Given
        val matchingBeer = beer { withId(1L).withName("Punk IPA") }
        val nonMatchingBeer = beer { withId(2L).withName("Elvis Juice") }
        val beersToFilter = listOf(
            matchingBeer,
            nonMatchingBeer
        )
        val expectedBeers = listOf(matchingBeer)
        val useCase = FilterBeers()

        //When
        val result = useCase(beers = beersToFilter, query = "  Punk  ")

        //Then
        assertEquals(expectedBeers, result)
    }

    @Test
    fun `given non matching query when invoked then returns empty list`() {
        //Given
        val firstBeer = beer { withId(1L).withName("Punk IPA") }
        val secondBeer = beer { withId(2L).withName("Elvis Juice") }
        val beersToFilter = listOf(
            firstBeer,
            secondBeer
        )
        val expectedBeers = emptyList<Beer>()
        val useCase = FilterBeers()

        //When
        val result = useCase(beers = beersToFilter, query = "Lager")

        //Then
        assertEquals(expectedBeers, result)
    }

    @Test
    fun `given matching beers in different positions when invoked then preserves original order`() {
        //Given
        val firstMatchingBeer = beer { withId(1L).withName("Punk IPA") }
        val nonMatchingBeer = beer { withId(2L).withName("Elvis Juice") }
        val secondMatchingBeer = beer { withId(3L).withName("Punk AF") }
        val beersToFilter = listOf(
            firstMatchingBeer,
            nonMatchingBeer,
            secondMatchingBeer
        )
        val expectedBeers = listOf(
            firstMatchingBeer,
            secondMatchingBeer
        )
        val useCase = FilterBeers()

        //When
        val result = useCase(beers = beersToFilter, query = "Punk")

        //Then
        assertEquals(expectedBeers, result)
    }

    @Test
    fun `given query matching description but not name when invoked then returns empty list`() {
        //Given
        val beerWithMatchingDescription = beer {
            withId(1L)
            withName("Elvis Juice")
            withDescription("Punk style citrus beer")
        }
        val beersToFilter = listOf(beerWithMatchingDescription)
        val expectedBeers = emptyList<Beer>()
        val useCase = FilterBeers()

        //When
        val result = useCase(beers = beersToFilter, query = "Punk")

        //Then
        assertEquals(expectedBeers, result)
    }
}
