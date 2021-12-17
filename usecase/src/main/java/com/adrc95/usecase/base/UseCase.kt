package com.adrc95.usecase.base

import arrow.core.Either
import com.adrc95.domain.exception.ApiError

abstract class UseCase<in Params, out Type> where Type: Any {

  abstract suspend fun run(params: Params): Either<ApiError, Type>

  class None
}