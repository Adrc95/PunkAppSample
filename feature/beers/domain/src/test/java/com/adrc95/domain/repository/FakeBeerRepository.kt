package com.adrc95.domain.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.exception.BeerNotFound
import com.adrc95.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.asStateFlow

class FakeBeerRepository : BeerRepository {
    private val beersByPage = MutableStateFlow<Map<Int, List<Beer>>>(emptyMap())

    override fun getBeers(page: Int): Flow<List<Beer>> =
        beersByPage.asStateFlow().map { pages -> pages[page].orEmpty() }

    fun setBeers(page: Int, items: List<Beer>) {
        beersByPage.value = beersByPage.value.toMutableMap().apply {
            this[page] = items
        }
    }

    override suspend fun refreshBeers(
        page: Int,
        force: Boolean
    ): ApiError? = null

    override suspend fun getBeer(id: Long): Either<ApiError, Beer> =
        beersByPage.value.values
            .flatten()
            .find { it.id == id }
            ?.right()
            ?: BeerNotFound().left()
}
