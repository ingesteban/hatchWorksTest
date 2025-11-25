package dev.esteban.network.error

import dev.esteban.network.Mapper
import dev.esteban.network.NON_HTTP_EXCEPTION
import dev.esteban.network.ResponseState
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val HTTP_CODE_400 = 400
private const val HTTP_CODE_401 = 401
private const val HTTP_CODE_404 = 404
private const val HTTP_CODE_409 = 409
private const val HTTP_CODE_500 = 500
private const val HTTP_CODE_502 = 502

class NetworkErrorMapper<T> : Mapper<Throwable, ResponseState<T>> {
    override fun apply(input: Throwable): ResponseState<T> =
        ResponseState.Error(
            errorType = errorViewState(throwable = input),
            errorBody = getErrorBody(throwable = input),
            httpErrorCode = getHttpCodeError(throwable = input),
        )

    private fun errorViewState(throwable: Throwable): ErrorType {
        return when (throwable) {
            is UnknownHostException -> ErrorType.UNKNOWN
            is HttpException -> {
                return when (throwable.code()) {
                    HTTP_CODE_400 -> ErrorType.DEFAULT
                    HTTP_CODE_401 -> ErrorType.UNAUTHORIZED
                    HTTP_CODE_404 -> ErrorType.NOT_FOUND
                    HTTP_CODE_409 -> ErrorType.CONFLICT
                    HTTP_CODE_500 -> ErrorType.SERVER
                    HTTP_CODE_502 -> ErrorType.CONNECTION_ERROR
                    else -> ErrorType.SERVER
                }
            }

            is SocketTimeoutException -> ErrorType.REQUEST_TIME_OUT
            else -> ErrorType.UNKNOWN
        }
    }

    private fun getHttpCodeError(throwable: Throwable): Int {
        return when (throwable) {
            is HttpException -> {
                return throwable.code()
            }

            else -> {
                NON_HTTP_EXCEPTION
            }
        }
    }

    private fun getErrorBody(throwable: Throwable): ErrorBody =
        try {
            when (throwable) {
                is HttpException -> {
                    val errorBodyString = throwable.response()?.errorBody()?.string()
                    if (!errorBodyString.isNullOrEmpty()) {
                        Json.decodeFromString<ErrorBody>(errorBodyString)
                    } else {
                        ErrorBody(statusMessage = "No message string")
                    }
                }

                else -> ErrorBody(statusMessage = "No http exception")
            }
        } catch (e: Exception) {
            ErrorBody(statusMessage = e.message.orEmpty())
        }
}
