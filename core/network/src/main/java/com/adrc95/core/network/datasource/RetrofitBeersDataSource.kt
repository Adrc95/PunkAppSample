package com.adrc95.core.network.datasource

import arrow.core.Either
import com.adrc95.core.common.tryCall
import com.adrc95.core.network.service.PunkApiService
import com.adrc95.core.network.mapper.toDomain
import com.adrc95.feature.beers.data.datasource.BeersNetworkDataSource
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import javax.inject.Inject

class RetrofitBeersDataSource @Inject constructor(private val punkApiService: PunkApiService) :
    BeersNetworkDataSource {

    override suspend fun getBeers(page: Int, itemsPerPage: Int): Either<ApiError, List<Beer>> =
        tryCall { punkApiService.getBeers(page, itemsPerPage) }.map { it.toDomain() }

    override suspend fun getBeer(id: Long): Either<ApiError, Beer> =
        tryCall { punkApiService.getBeer(id) }.map { it.toDomain() }

}
