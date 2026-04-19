package com.adrc95.feature.beers.data.repository

import arrow.core.Either
import arrow.core.right
import com.adrc95.domain.BeerPagingConfig
import com.adrc95.feature.beers.data.datasource.BeerPageCacheLocalDataSource
import com.adrc95.feature.beers.data.datasource.BeersLocalDataSource
import com.adrc95.feature.beers.data.datasource.BeersNetworkDataSource
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.model.Beer
import com.adrc95.domain.repository.BeerRepository
import com.adrc95.feature.beers.data.CACHE_MAX_AGE_MILLIS
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BeerRepositoryImpl @Inject constructor(
    private val networkDataSource: BeersNetworkDataSource,
    private val localDataSource: BeersLocalDataSource,
    private val beerPageCacheLocalDataSource: BeerPageCacheLocalDataSource
) : BeerRepository {

    private val refreshMutex = Mutex()

    override fun getBeers(page: Int): Flow<List<Beer>> = channelFlow {
        launch {
            refreshBeers(page)
        }

        localDataSource.getBeers(page, BeerPagingConfig.ITEMS_PER_PAGE)
            .collectLatest { beers ->
                send(beers)
            }
    }

    override suspend fun refreshBeers(page: Int, force: Boolean): ApiError? =
        refreshMutex.withLock {
            val itemsPerPage = BeerPagingConfig.ITEMS_PER_PAGE
            val localBeers = localDataSource.getBeers(page, itemsPerPage).first()
            if (!force && !shouldRefresh(localBeers, page, itemsPerPage)) {
                return@withLock null
            }

            when (val remoteResult = networkDataSource.getBeers(page, itemsPerPage)) {
                is Either.Right -> {
                    localDataSource.saveBeers(page, itemsPerPage, remoteResult.value)
                    beerPageCacheLocalDataSource.update(
                        page = page,
                        itemsPerPage = itemsPerPage,
                        updatedAt = System.currentTimeMillis()
                    )
                    null
                }

                is Either.Left -> {
                    if (localBeers.isNotEmpty()) {
                        null
                    } else {
                        remoteResult.value
                    }
                }
            }
        }

    override suspend fun getBeer(id: Long): Either<ApiError, Beer> {
        val localBeer = localDataSource.getBeer(id)
        if (localBeer != null) return localBeer.right()

        return when (val remoteResult = networkDataSource.getBeer(id)) {
            is Either.Right -> localDataSource.saveBeer(remoteResult.value).run {
                remoteResult.value.right()
            }

            is Either.Left -> remoteResult
        }
    }

    private suspend fun shouldRefresh(
        localBeers: List<Beer>,
        page: Int,
        itemsPerPage: Int
    ): Boolean {
        if (localBeers.isEmpty()) return true

        val updatedAt = beerPageCacheLocalDataSource.getUpdatedAt(page, itemsPerPage)
            ?: return true

        return System.currentTimeMillis() - updatedAt > CACHE_MAX_AGE_MILLIS
    }
}
