package com.mx.liftechnology.data.util

/**
 * A sealed class representing different types of service failures.
 *
 * @property message A descriptive message for the failure.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class FailureService(val message: String) {
    /** Represents a network connection error. */
    data object NetworkError : FailureService("Error de conexión a Internet")

    /** Represents a server-side error. */
    data object ServerError : FailureService("Error en el servidor, intenta más tarde")

    /** Represents a "Not Found" error (e.g., 404). */
    data object NotFound : FailureService("El recurso solicitado no existe")

    /** Represents an "Unauthorized" error (e.g., 401). */
    data object Unauthorized : FailureService("No tienes permisos para realizar esta acción")

    /** Represents a "Bad Request" error (e.g., 400). */
    data object BadRequest : FailureService("Solicitud incorrecta, revisa los datos enviados")

    /** Represents a timeout error. */
    data object Timeout : FailureService("La solicitud tardó demasiado en responder")

    /**
     * Represents an unknown or unexpected error.
     * @param message A custom message describing the error.
     */
    class UnknownError(message: String) : FailureService(message)
}