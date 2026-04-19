package com.adrc95.feature.beers.data.datasource

import com.adrc95.domain.model.Beer
import kotlinx.coroutines.flow.Flow

interface BeersLocalDataSource {

    suspend fun saveBeer(beer: Beer)

    suspend fun saveBeers(page: Int, itemsPerPage: Int, beers: List<Beer>)

    suspend fun getBeer(id: Long): Beer?

    fun getBeers(page: Int, itemsPerPage: Int): Flow<List<Beer>>
}
