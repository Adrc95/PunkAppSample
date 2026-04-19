package com.adrc95.core.database.datasource

import com.adrc95.core.database.dao.BeerPageCacheDao
import com.adrc95.core.database.entity.BeerPageCacheEntity
import com.adrc95.feature.beers.data.datasource.BeerPageCacheLocalDataSource
import javax.inject.Inject

class RoomBeerPageCacheDataSource @Inject constructor(
    private val beerPageCacheDao: BeerPageCacheDao
) : BeerPageCacheLocalDataSource {

    override suspend fun getUpdatedAt(page: Int, itemsPerPage: Int): Long? =
        beerPageCacheDao.getUpdatedAt(page, itemsPerPage)

    override suspend fun update(page: Int, itemsPerPage: Int, updatedAt: Long) {
        beerPageCacheDao.upsert(
            BeerPageCacheEntity(
                page = page,
                itemsPerPage = itemsPerPage,
                updatedAt = updatedAt
            )
        )
    }
}
