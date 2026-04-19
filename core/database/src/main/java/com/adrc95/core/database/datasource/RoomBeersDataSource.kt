package com.adrc95.core.database.datasource

import com.adrc95.core.database.dao.PunkDao
import com.adrc95.core.database.mapper.toDomain
import com.adrc95.core.database.mapper.toEntity
import com.adrc95.feature.beers.data.datasource.BeersLocalDataSource
import com.adrc95.domain.model.Beer
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomBeersDataSource @Inject constructor(private val punkDao: PunkDao) :
    BeersLocalDataSource {

    override suspend fun saveBeer(beer: Beer) {
        punkDao.insert(beer.toEntity())
    }

    override suspend fun saveBeers(page: Int, itemsPerPage: Int, beers: List<Beer>) {
        punkDao.replacePage(
            page = page,
            itemsPerPage = itemsPerPage,
            beers = beers.map { it.toEntity() }
        )
    }

    override suspend fun getBeer(id: Long): Beer? =
        punkDao.getBeer(id)?.toDomain()

    override fun getBeers(page: Int, itemsPerPage: Int): Flow<List<Beer>> =
        punkDao.observeBeers(page, itemsPerPage)
            .map { beers -> beers.map { it.toDomain() } }

}
