package com.mx.liftechnology.data.util

/**
 * Interfaz base sellada para representar cualquier tipo de error en la capa de dominio.
 * Permite agrupar diferentes categorías de errores (red, locales, etc.) bajo un tipo común.
 * @author PelkiDev
 * @version 1.0.0
 */
sealed interface Error

/**
 * Representa errores específicos de validaciones locales o de lógica de negocio
 * que ocurren antes de una llamada de red.
 * @author PelkiDev
 * @version 1.0.0
 */
enum class UserError {
    /** Error que indica que uno o más campos requeridos para la operación están vacíos o son inválidos. */
    SHOW_GENERIC_ERROR,
    SHOW_SPECIFIC_ERROR,
    SHOW_INCOMPLETE_ERROR,
    UNAUTHORIZED,
    LOGS
}


/**
 * Representa errores específicos de validaciones locales o de lógica de negocio
 * que ocurren antes de una llamada de red.
 * @author PelkiDev
 * @version 1.0.0
 */
enum class LocalError : Error {
    /** Error que indica que uno o más campos requeridos para la operación están vacíos o son inválidos. */
    USER_INCOMPLETE_DATA,
    RESPONSE_INCOMPLETE_DATA,
    CATCH,
    EMPTY
}


/**
 * Representa errores que ocurren durante la comunicación con el servidor o la red.
 * @author PelkiDev
 * @version 1.0.0
 */
enum class NetworkError : Error {
    TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNAUTHORIZED,
    NOT_FOUND,
    CONFLICT,
    BAD_REQUEST,
    UNKNOWN,
    UNKNOWN_REGISTER,
    EMPTY
}

/**
 * Clase sellada que encapsula el resultado de una operación en la capa de dominio.
 * Puede ser un éxito ([SuccessResult]) o un error ([ErrorResult]).
 *
 * @param D El tipo de dato en caso de éxito.
 * @param E El tipo de error, que debe implementar la interfaz [Error].
 * @author PelkiDev
 * @version 1.0.0
 */
sealed class ModelResult<out D, out E: Error>

/**
 * Representa un resultado exitoso, conteniendo los datos de la operación.
 * @param data Los datos resultantes de la operación.
 */
data class SuccessResult<out D>(val data: D) : ModelResult<D, Nothing>()

/**
 * Representa un resultado fallido, conteniendo el error específico.
 * @param error El error que ocurrió durante la operación.
 */
data class ErrorResult<out E: Error>(val error: E) : ModelResult<Nothing, E>()
