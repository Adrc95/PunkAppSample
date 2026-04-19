package com.adrc95.domain.usecase

import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.repository.BeerRepository
import javax.inject.Inject

class RefreshBeers @Inject constructor(private val beerRepository: BeerRepository) {
    suspend operator fun invoke(
        page: Int,
        force: Boolean = false,
    ): ApiError? = beerRepository.refreshBeers(page, force)
}
