package com.adrc95.core.common

import arrow.core.Either
import com.adrc95.domain.exception.Error
import java.io.IOException
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ExtensionTest {

    @Test
    fun `given successful action when try call is invoked then returns right with result`() = runTest {
        //Given
        val expectedResult = "Punk IPA"

        //When
        val result = tryCall { expectedResult }

        //Then
        assertTrue(result is Either.Right)
        assertEquals(expectedResult, (result as Either.Right).value)
    }

    @Test
    fun `given action throwing io exception when try call is invoked then returns connectivity error`() = runTest {
        //Given
        val expectedError = Error.Connectivity

        //When
        val result = tryCall<String> { throw IOException() }

        //Then
        assertTrue(result is Either.Left)
        assertEquals(expectedError, (result as Either.Left).value)
    }

    @Test
    fun `given action throwing http exception when try call is invoked then returns server error`() = runTest {
        //Given
        val expectedError = Error.Server(404)
        val httpException = HttpException(
            Response.error<String>(
                404,
                "{}".toResponseBody("application/json".toMediaType())
            )
        )

        //When
        val result = tryCall<String> { throw httpException }

        //Then
        assertTrue(result is Either.Left)
        assertEquals(expectedError, (result as Either.Left).value)
    }

    @Test
    fun `given action throwing unexpected exception when try call is invoked then returns unknown error`() = runTest {
        //Given
        val expectedError = Error.Unknown("unknown")

        //When
        val result = tryCall<String> { throw IllegalStateException("unknown") }

        //Then
        assertTrue(result is Either.Left)
        assertEquals(expectedError, (result as Either.Left).value)
    }
}
