package com.mx.liftechnology.data.util

sealed class FailureService(val message: String) {
    data object NetworkError : FailureService("Error de conexión a Internet")
    data object ServerError : FailureService("Error en el servidor, intenta más tarde")
    data object NotFound : FailureService("El recurso solicitado no existe")
    data object Unauthorized : FailureService("No tienes permisos para realizar esta acción")
    data object BadRequest : FailureService("Solicitud incorrecta, revisa los datos enviados")
    data object Timeout : FailureService("La solicitud tardó demasiado en responder")
    class UnknownError(message: String) : FailureService(message)
}