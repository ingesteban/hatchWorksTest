package dev.esteban.network

import dev.esteban.network.error.ErrorBody
import dev.esteban.network.error.ErrorType

const val NON_HTTP_EXCEPTION = -1

sealed class ResponseState<out T> {
    object Loading : ResponseState<Nothing>()

    data class Success<T>(val response: T) : ResponseState<T>()

    data class Error(
        val errorType: ErrorType = ErrorType.DEFAULT,
        val errorBody: ErrorBody? = null,
        val httpErrorCode: Int = NON_HTTP_EXCEPTION,
    ) : ResponseState<Nothing>()

    object Idle : ResponseState<Nothing>()
}
