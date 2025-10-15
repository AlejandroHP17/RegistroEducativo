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
    data object NetworkError : FailureService("Error de conexión a Internet")

    /** Representa un error interno del servidor. */
    data object ServerError : FailureService("Error en el servidor, intenta más tarde")

    /** Representa un error de "No Encontrado" (ej: 404). */
    data object NotFound : FailureService("El recurso solicitado no existe")

    /** Representa un error de "No Autorizado" (ej: 401). */
    data object Unauthorized : FailureService("No tienes permisos para realizar esta acción")

    /** Representa un error de "Solicitud Incorrecta" (ej: 400). */
    data object BadRequest : FailureService("Solicitud incorrecta, revisa los datos enviados")

    /** Representa un error de tiempo de espera agotado. */
    data object Timeout : FailureService("La solicitud tardó demasiado en responder")

    /**
     * Representa un error desconocido o inesperado.
     * @param message Un mensaje personalizado que describe el error.
     */
    class UnknownError(message: String) : FailureService(message)
}