package com.adrc95.punkappsample.data.service

import arrow.core.Either
import com.adrc95.domain.exception.ApiError
import com.adrc95.punkappsample.data.Beer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkApiService {

    @GET("beers")
    suspend fun getBeers(@Query("page") page: Int,
                  @Query("per_page") perPage: Int
    ): Either<ApiError, List<Beer>>

    @GET("beers/{id}")
    suspend fun getBeer(@Path("id") id: Long): Either<ApiError, List<Beer>>
}