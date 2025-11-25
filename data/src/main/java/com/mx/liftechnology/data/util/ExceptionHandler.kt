/**
 * @file Proporciona un manejador de excepciones para convertir errores en un formato de servicio de fallo definido.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.util

import com.mx.liftechnology.core.util.logInfo

/**
 * Objeto que gestiona las excepciones y las convierte en un [com.mx.liftechnology.domain.util.NetworkError].
 * Centraliza el manejo de errores de red y de servidor, traduciéndolos a un modelo de error unificado.
 *
 * @author Pelkidev
 * @version 1.0.0
 */

object NetworkException {
    /**
     * Gestiona una [Throwable] y la convierte en un [com.mx.liftechnology.domain.util.NetworkError].
     *
     * @param exception La excepción a gestionar.
     * @return Un [com.mx.liftechnology.domain.util.NetworkError] que representa la excepción gestionada.
     */
    fun handleException(exception: Throwable): NetworkModelError {
        val error = when (exception) {
            is java.net.UnknownHostException -> NetworkModelError.NO_INTERNET  // No hay conexión a Internet
            is java.net.ConnectException -> NetworkModelError.NO_INTERNET  // No se puede establecer conexión con el servidor
            is java.net.SocketTimeoutException -> NetworkModelError.TIMEOUT // Timeout de conexión
            is retrofit2.HttpException -> {
                when (exception.code()) {
                    400 -> NetworkModelError.BAD_REQUEST
                    401 -> NetworkModelError.UNAUTHORIZED
                    403 -> NetworkModelError.WITHOUT_ACCESS
                    404 -> NetworkModelError.NOT_FOUND
                    409 -> NetworkModelError.CONFLICT
                    429 -> NetworkModelError.TOO_MANY_REQUESTS
                    430 -> NetworkModelError.NOT_ACTIVE
                    500 -> NetworkModelError.SERVER_ERROR
                    else -> NetworkModelError.UNKNOWN
                }
            }
            else -> NetworkModelError.UNKNOWN
        }
        logInfo(error.name, "falla servicio")
        return error
    }
}
