package com.adrc95.data.repository

import arrow.core.Either
import arrow.core.right
import com.adrc95.data.source.BeersLocalDataSource
import com.adrc95.data.source.BeersNetworkDataSource
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import com.adrc95.domain.repository.BeerRepository
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val networkDataSource: BeersNetworkDataSource,
    private val localDataSource: BeersLocalDataSource
) : BeerRepository {

    override suspend fun getBeers(page: Int, itemsPerPage: Int): Either<ApiError, List<Beer>> =
        networkDataSource.getBeers(page, itemsPerPage)

    override suspend fun getBeer(id: Long): Either<ApiError, Beer> =
        localDataSource.getBeer(id).fold(
            { networkDataSource.getBeer(id).map { localDataSource.saveBeer(it); it } },
            { it.right() })

}
