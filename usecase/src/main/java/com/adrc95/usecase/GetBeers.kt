package com.adrc95.usecase

import arrow.core.Either
import com.adrc95.data.repository.BeersRepository
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import javax.inject.Inject

class GetBeers @Inject constructor(private val beersRepository: BeersRepository) {

    suspend operator fun invoke(
        page: Int,
        itemsPerPage: Int,
    ): Either<ApiError, List<Beer>> = beersRepository.getBeers(page, itemsPerPage)

}