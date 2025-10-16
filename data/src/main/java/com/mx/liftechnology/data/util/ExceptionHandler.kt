/**
 * @file Proporciona un manejador de excepciones para convertir errores en un formato de servicio de fallo definido.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.util
/**
 * Objeto que gestiona las excepciones y las convierte en un [FailureService].
 * Centraliza el manejo de errores de red y de servidor, traduciéndolos a un modelo de error unificado.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ExceptionHandler {
    /**
     * Gestiona una [Throwable] y la convierte en un [FailureService].
     *
     * @param exception La excepción a gestionar.
     * @return Un [FailureService] que representa la excepción gestionada.
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
                    else -> FailureService.UnknownError(MessageError.CODE_ERROR_MESSAGE + "${exception.code()}")
                }
            }

            else -> FailureService.UnknownError(exception.localizedMessage ?: MessageError.UNKNOWN_ERROR_MESSAGE)
        }
    }
}
