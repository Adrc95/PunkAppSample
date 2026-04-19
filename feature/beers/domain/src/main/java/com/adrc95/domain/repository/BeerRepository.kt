package com.adrc95.domain.repository

import arrow.core.Either
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import kotlinx.coroutines.flow.Flow

interface BeerRepository {

    fun getBeers(page: Int): Flow<List<Beer>>

    suspend fun refreshBeers(page: Int, force: Boolean = false): ApiError?

    suspend fun getBeer(id: Long): Either<ApiError, Beer>
}
