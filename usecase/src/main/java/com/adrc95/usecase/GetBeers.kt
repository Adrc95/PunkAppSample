package com.adrc95.usecase

import com.adrc95.data.repository.BeersRepository
import com.adrc95.domain.model.Beer
import com.adrc95.usecase.base.UseCase

class GetBeers(private val beersRepository: BeersRepository) :
    UseCase<GetBeers.Params, List<Beer>>() {

  override suspend fun run(params: Params) = beersRepository.getBeers(params.page, params.itemsPerPage)

  data class Params(val page: Int, val itemsPerPage: Int)

}