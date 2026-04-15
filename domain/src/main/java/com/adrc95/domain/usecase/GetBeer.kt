package com.adrc95.domain.usecase

import arrow.core.Either
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import com.adrc95.domain.repository.BeerRepository
import javax.inject.Inject

class GetBeer @Inject constructor(private val beerRepository: BeerRepository) {
    suspend operator fun invoke(id: Long): Either<ApiError, Beer> = beerRepository.getBeer(id)
}
