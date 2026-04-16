package com.adrc95.data.source

import arrow.core.Either
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer

interface BeersLocalDataSource {

    suspend fun saveBeer(beer: Beer): Either<ApiError, Unit>

    suspend fun getBeer(id: Long) : Either<ApiError, Beer>
}