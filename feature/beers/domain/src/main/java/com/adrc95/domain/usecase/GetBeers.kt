package com.adrc95.domain.usecase

import arrow.core.Either
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import com.adrc95.domain.repository.BeerRepository
import javax.inject.Inject

class GetBeers @Inject constructor(private val beerRepository: BeerRepository) {
    suspend operator fun invoke(
        page: Int,
        itemsPerPage: Int,
    ): Either<ApiError, List<Beer>> = beerRepository.getBeers(page, itemsPerPage)
}
