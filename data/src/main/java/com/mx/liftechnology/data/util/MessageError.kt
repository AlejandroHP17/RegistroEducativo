package com.mx.liftechnology.data.util

@Deprecated("seran en resources string")
object MessageError{
    /**
     * Mensaje de error para el log cuando una respuesta 200 OK llega con un cuerpo
     * o campo de datos nulo, violando el contrato esperado de la API.
     */
    const val UNEXPECTED_NULL_BODY_ERROR_MESSAGE = "Respuesta 200 OK pero el cuerpo de datos es nulo."

    /**
     * Mensaje de error para el log cuando hay error a la conexión de la red.
     */
    const val CONNECTION_INTERNET_ERROR_MESSAGE = "ModelError de conexión a Internet"

    /**
     * Mensaje de error para el log cuando hay error interno del servidor.
     */
    const val SERVER_ERROR_MESSAGE = "ModelError en el servidor, intenta más tarde"

    /**
     * Mensaje de error para el log cuando hay error de "No Encontrado" (ej: 404).
     */
    const val NOT_FOUND_ERROR_MESSAGE = "El recurso solicitado no existe"

    /**
     * Mensaje de error para el log cuando hay error de "No Autorizado" (ej: 401).
     */
    const val UNAUTHORIZED_ERROR_MESSAGE = "No tienes permisos para realizar esta acción"

    /**
     * Mensaje de error para el log cuando hay error de "Solicitud Incorrecta" (ej: 400).
     */
    const val INCORRECT_REQUEST_ERROR_MESSAGE = "Solicitud incorrecta, revisa los datos enviados"

    /**
     * Mensaje de error para el log cuando hay error de "Solicitud Incorrecta" (ej: 400).
     */
    const val TOO_MANY_REQUESTS = "Muchas peticiones"

    /**
     * Mensaje de error para el log cuando hay error de tiempo de espera agotado.
     */
    const val TIMEOUT_ERROR_MESSAGE = "La solicitud tardó demasiado en responder"

    /**
     * Mensaje de error del tipo de codigo.
     */
    const val CODE_ERROR_MESSAGE = "Codigo de error: "

    /**
     * Mensaje de error para el log cuando hay error desconocido
     */
    const val UNKNOWN_ERROR_MESSAGE = "ModelError desconocido"


}