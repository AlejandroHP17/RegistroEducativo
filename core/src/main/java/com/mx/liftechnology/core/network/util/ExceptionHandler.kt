package com.mx.liftechnology.core.network.util

object ExceptionHandler {
    fun handleException(exception: Throwable): FailureService {
        return when (exception) {
            is java.net.UnknownHostException -> FailureService.NetworkError  // No hay conexión a Internet
            is java.net.SocketTimeoutException -> FailureService.Timeout  // Timeout de conexión
            is retrofit2.HttpException -> {
                when (exception.code()) {
                    400 -> FailureService.BadRequest
                    401 -> FailureService.Unauthorized
                    404 -> FailureService.NotFound
                    500 -> FailureService.Timeout
                    else -> FailureService.UnknownError("Código de error: ${exception.code()}")
                }
            }
            else -> FailureService.UnknownError(exception.localizedMessage ?: "Error desconocido")
        }
    }
}
