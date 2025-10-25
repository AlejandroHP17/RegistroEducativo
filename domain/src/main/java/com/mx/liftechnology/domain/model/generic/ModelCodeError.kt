/**
 * @file Define los códigos y mensajes de error estandarizados para toda la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.generic

/**
 * Objeto que centraliza los mensajes de error constantes utilizados en la aplicación.
 * Esto asegura la consistencia en la comunicación de errores tanto a nivel de sistema como de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ModelCodeError {
    //region Errores de Servicio
    /** Mensaje para excepciones genéricas capturadas en un bloque catch. */
    const val ERROR_CATCH: String = "Error que entra al catch"

    /** Mensaje para indicar que los datos recibidos o procesados están incompletos. */
    const val ERROR_INCOMPLETE_DATA: String = "Datos incompletos"

    /** Mensaje para errores de tiempo de espera (timeout) en las peticiones. */
    const val ERROR_TIMEOUT: String = "TiemOut"

    /** Mensaje para errores de autorización, comúnmente por token expirado. */
    const val ERROR_UNAUTHORIZED: String = "Token expirado"

    /** Mensaje para errores genéricos en operaciones CRUD (Crear, Leer, Actualizar, Eliminar). */
    const val ERROR_DATA: String = "Error CRUD"

    /** Mensaje para errores genéricos en operaciones CRUD (Crear, Leer, Actualizar, Eliminar). */
    const val ERROR_TOO_MANY_REQUESTS: String = "Muchas peticiones"

    /** Mensaje para errores inesperados o no clasificados. */
    const val ERROR_UNKNOWN: String = "Error inesperado"

    /** Mensaje para indicar que una lista o conjunto de datos está vacío. */
    const val ERROR_EMPTY: String = "Lista vacia"
    //endregion



    //region Errores para el Usuario
    /** Mensaje de error crítico mostrado al usuario, sugiriendo reintentar más tarde. */
    const val ERROR_CRITICAL: String = "Ha ocurrido un error, intente más tarde"

    /** Mensaje para credenciales de inicio de sesión incorrectas. */
    const val ERROR_VALIDATION_LOGIN: String = "Usuario o contraseña incorrectos, por favor valide nuevamente su información"

    /** Mensaje cuando falla el registro de un nuevo usuario. */
    const val ERROR_VALIDATION_REGISTER_USER: String = "No se ha podido registrar el correo. Verifique que los datos ingresados sean correctos"

    /** Mensaje para email o código de activación incorrectos durante el registro. */
    const val ERROR_VALIDATION_REGISTER_INFO: String = "Correo o código incorrectos, verifique su información"

    /** Mensaje genérico cuando una actualización de datos falla. */
    const val ERROR_VALIDATION: String = "No se logro actualizar la información, intente más tarde"
    //endregion
}
