package com.mx.liftechnology.domain.model.generic

/**
 * Object that contains constants for error messages used throughout the application.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ModelCodeError {
    // Service Errors
    /** Error message for exceptions caught in a catch block. */
    const val ERROR_CATCH: String = "Error que entra al catch"

    /** Error message for incomplete data. */
    const val ERROR_INCOMPLETE_DATA: String = "Datos incompletos"

    /** Error message for a timeout. */
    const val ERROR_TIMEOUT: String = "TiemOut"

    /** Error message for an expired token. */
    const val ERROR_UNAUTHORIZED: String = "Token expirado"

    /** Error message for a CRUD operation error. */
    const val ERROR_DATA: String = "Error CRUD"

    /** Error message for an unexpected error. */
    const val ERROR_UNKNOWN: String = "Error inesperado"

    /** Error message for an empty list. */
    const val ERROR_EMPTY: String = "Lista vacia"

    // User-facing Errors
    /** Critical error message for the user. */
    const val ERROR_CRITICAL: String = "Ha ocurrido un error, intente más tarde"

    /** Error message for incorrect login credentials. */
    const val ERROR_VALIDATION_LOGIN: String = "Usuario o contraseña incorrectos, por favor valide nuevamente su información"

    /** Error message for a failed user registration. */
    const val ERROR_VALIDATION_REGISTER_USER: String = "No se ha podido registrar el correo. Verifique que los datos ingresados sean correctos"

    /** Error message for incorrect registration info (email or code). */
    const val ERROR_VALIDATION_REGISTER_INFO: String = "Correo o código incorrectos, verifique su información"

    /** Error message for a failed data update. */
    const val ERROR_VALIDATION: String = "No se logro actualizar la información, intente más tarde"
}
