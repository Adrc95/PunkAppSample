package com.adrc95.punkappsample.data.datasource

import arrow.core.Either
import com.adrc95.data.source.BeersNetworkDataSource
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.data.mapper.toDomain
import com.adrc95.punkappsample.data.service.APIService
import com.adrc95.punkappsample.data.service.PunkApiService

class RemoteBeersDataSource(private val api: APIService<PunkApiService>) :
    BeersNetworkDataSource {

    override suspend fun getBeers(page: Int, itemsPerPage: Int): Either<ApiError, List<Beer>> =
        api.service.getBeers(page, itemsPerPage).map { it.toDomain() }

    override suspend fun getBeer(id: Long): Either<ApiError, Beer>  =
        api.service.getBeer(id).map { it.toDomain()[0]}

}