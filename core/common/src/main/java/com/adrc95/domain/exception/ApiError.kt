package com.adrc95.domain.exception

sealed class Error {
    object Connectivity : Error()

    data class Server(val code: Int) : Error()

    data class Unknown(val message: String) : Error()

    abstract class FeatureFailure : Error()
}

typealias ApiError = Error
