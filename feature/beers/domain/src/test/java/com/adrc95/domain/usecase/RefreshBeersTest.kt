package com.adrc95.domain.usecase

import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.exception.Error
import com.adrc95.domain.repository.BeerRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RefreshBeersTest {

    @MockK
    lateinit var repository: BeerRepository

    private lateinit var useCase: RefreshBeers

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = RefreshBeers(beerRepository = repository)
    }

    @Test
    fun `given successful refresh when invoked then returns null`() = runTest {
        //Given
        coEvery { repository.refreshBeers(page = 1, force = false) } returns null

        //When
        val result = useCase(page = 1)

        //Then
        assertNull(result)
        coVerify(exactly = 1) { repository.refreshBeers(page = 1, force = false) }
    }

    @Test
    fun `given failed refresh when invoked then returns api error`() = runTest {
        //Given
        val expectedError: ApiError = Error.Connectivity
        coEvery { repository.refreshBeers(page = 1, force = true) } returns expectedError

        //When
        val result = useCase(page = 1, force = true)

        //Then
        assertEquals(expectedError, result)
        coVerify(exactly = 1) { repository.refreshBeers(page = 1, force = true) }
    }
}
