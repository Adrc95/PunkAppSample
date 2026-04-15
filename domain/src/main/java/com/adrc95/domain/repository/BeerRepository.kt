package com.adrc95.domain.repository

import arrow.core.Either
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer

interface BeerRepository {

    suspend fun getBeers(page: Int, itemsPerPage: Int): Either<ApiError, List<Beer>>

    suspend fun getBeer(id: Long): Either<ApiError, Beer>
}
