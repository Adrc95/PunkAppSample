package com.adrc95.domain.usecase

import com.adrc95.domain.model.Beer
import com.adrc95.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeers @Inject constructor(private val beerRepository: BeerRepository) {
    operator fun invoke(
        page: Int,
    ): Flow<List<Beer>> = beerRepository.getBeers(page)
}
