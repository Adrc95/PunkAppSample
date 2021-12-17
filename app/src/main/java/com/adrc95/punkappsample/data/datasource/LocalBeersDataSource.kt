package com.adrc95.punkappsample.data.datasource

import arrow.core.Either
import com.adrc95.data.source.BeersLocalDataSource
import com.adrc95.domain.exception.ApiError
import com.adrc95.domain.exception.BeerNotFound
import com.adrc95.domain.model.Beer
import com.adrc95.punkappsample.data.dao.PunkDao
import com.adrc95.punkappsample.data.mapper.toDomain
import com.adrc95.punkappsample.data.mapper.toEntity

class LocalBeersDataSource(private val punkDao: PunkDao) :
    BeersLocalDataSource {

    override suspend fun getBeer(id: Long): Either<ApiError, Beer> {
        val bear = punkDao.getBeer(id)
        return if (bear == null) {
            Either.Left(BeerNotFound())
        } else {
            Either.Right(bear.toDomain())
        }
    }

    override suspend fun saveBeer(beer: Beer): Either<ApiError, Unit> {
       punkDao.insert(beer.toEntity())
       return  Either.Right(Unit)
    }

}