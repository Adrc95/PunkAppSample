package com.adrc95.domain.usecase

import arrow.core.Either
import com.adrc95.domain.builder.beer
import com.adrc95.domain.exception.BeerNotFound
import com.adrc95.domain.repository.FakeBeerRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetBeerTest {

    @Test
    fun `given existing beer when invoke then returns beer`() = runTest {
        //Given
        val expectedBeer = beer { withId(1L) }
        val repository = FakeBeerRepository().apply {
            setBeers(page = 1, items = listOf(expectedBeer))
        }
        val useCase = GetBeer(beerRepository = repository)

        //When
        val result = useCase(1L)

        //Then
        assertTrue(result is Either.Right)
        assertEquals(expectedBeer, (result as Either.Right).value)
    }

    @Test
    fun `given missing beer when invoke then returns beer not found`() = runTest {
        //Given
        val beer = beer { withId(1L) }
        val repository = FakeBeerRepository().apply {
            setBeers(page = 1, items = listOf(beer))
        }
        val useCase = GetBeer(beerRepository = repository)

        //When
        val result = useCase(2L)

        //Then
        assertTrue(result is Either.Left)
        assertTrue((result as Either.Left).value is BeerNotFound)
    }
}
