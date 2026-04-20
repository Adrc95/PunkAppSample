package com.adrc95.domain.usecase

import com.adrc95.domain.builder.beer
import com.adrc95.domain.model.Beer
import com.adrc95.domain.repository.FakeBeerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetBeersTest {

    @Test
    fun `given existing beers when invoke then returns beer list`() = runTest {
        //Given
        val expectedBeers = listOf(
            beer { withId(1L) },
            beer { withId(2L) }
        )
        val otherPageBeers = listOf(
            beer { withId(3L) }
        )
        val repository = FakeBeerRepository().apply {
            setBeers(page = 1, items = expectedBeers)
            setBeers(page = 2, items = otherPageBeers)
        }
        val useCase = GetBeers(beerRepository = repository)

        //When
        val result = useCase(page = 1).first()

        //Then
        assertEquals(expectedBeers, result)
    }

    @Test
    fun `given beers in different pages when invoke with second page then returns second page beers`() = runTest {
        //Given
        val firstPageBeers = listOf(
            beer { withId(1L) }
        )
        val expectedBeers = listOf(
            beer { withId(2L) },
            beer { withId(3L) }
        )
        val repository = FakeBeerRepository().apply {
            setBeers(page = 1, items = firstPageBeers)
            setBeers(page = 2, items = expectedBeers)
        }
        val useCase = GetBeers(beerRepository = repository)

        //When
        val result = useCase(page = 2).first()

        //Then
        assertEquals(expectedBeers, result)
    }

    @Test
    fun `given no beers when invoke then returns empty list`() = runTest {
        //Given
        val repository = FakeBeerRepository()
        val useCase = GetBeers(beerRepository = repository)

        //When
        val result = useCase(page = 1).first()

        //Then
        assertEquals(emptyList<Beer>(), result)
    }
}
