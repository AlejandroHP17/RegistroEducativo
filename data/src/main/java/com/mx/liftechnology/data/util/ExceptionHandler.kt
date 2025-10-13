package com.mx.liftechnology.data.util

/**
 * An object that handles exceptions and converts them into a [FailureService].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ExceptionHandler {
    /**
     * Handles a given [Throwable] and converts it into a [FailureService].
     *
     * @param exception The exception to handle.
     * @return A [FailureService] representing the handled exception.
     */
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
