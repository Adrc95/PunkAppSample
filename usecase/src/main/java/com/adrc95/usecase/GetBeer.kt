package com.adrc95.usecase

import com.adrc95.data.repository.BeersRepository
import com.adrc95.domain.model.Beer
import com.adrc95.usecase.base.UseCase
import javax.inject.Inject

class GetBeer @Inject constructor(private val beersRepository: BeersRepository) :
    UseCase<GetBeer.Params, Beer>() {

    override suspend fun run(params: Params) = beersRepository.getBeer(params.id)

    data class Params(val id: Long)

}