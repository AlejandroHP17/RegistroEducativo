/**
 * @file Define los diferentes tipos de fallos de servicio que pueden ocurrir en la capa de datos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.util

/**
 * Clase sellada que representa los diferentes tipos de fallos de servicio.
 * Cada objeto o clase representa un tipo de error específico con un mensaje descriptivo.
 *
 * @property message El mensaje descriptivo del fallo.
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class FailureService(val message: String) {
    /** Representa un error de conexión a la red. */
    data object NetworkError : FailureService(MessageError.CONNECTION_INTERNET_ERROR_MESSAGE)

    /** Representa un error interno del servidor. */
    data object ServerError : FailureService(MessageError.SERVER_ERROR_MESSAGE)

    /** Representa un error de "No Encontrado" (ej: 404). */
    data object NotFound : FailureService(MessageError.NOT_FOUND_ERROR_MESSAGE)

    /** Representa un error de "No Autorizado" (ej: 401). */
    data object Unauthorized : FailureService(MessageError.UNAUTHORIZED_ERROR_MESSAGE)

    /** Representa un error de "Solicitud Incorrecta" (ej: 400). */
    data object BadRequest : FailureService(MessageError.INCORRECT_REQUEST_ERROR_MESSAGE)

    /** Representa un error de tiempo de espera agotado. */
    data object Timeout : FailureService(MessageError.TIMEOUT_ERROR_MESSAGE)

    /**
     * Representa un error desconocido o inesperado.
     * @param message Un mensaje personalizado que describe el error.
     */
    class UnknownError(message: String) : FailureService(message)
}