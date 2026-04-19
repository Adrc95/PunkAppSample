package com.adrc95.feature.beers.data.datasource

interface BeerPageCacheLocalDataSource {

    suspend fun getUpdatedAt(page: Int, itemsPerPage: Int): Long?

    suspend fun update(page: Int, itemsPerPage: Int, updatedAt: Long)
}
