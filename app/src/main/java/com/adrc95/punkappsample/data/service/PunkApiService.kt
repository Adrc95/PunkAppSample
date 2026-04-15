package com.adrc95.punkappsample.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PunkApiService {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<BeerDto>

    @GET("beers/{id}")
    suspend fun getBeer(@Path("id") id: Long): BeerDto
}
