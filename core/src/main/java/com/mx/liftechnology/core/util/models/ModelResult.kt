/**
 * @file Define los tipos de resultado y errores utilizados en la capa de datos.
 * 
 * Este archivo proporciona una estructura unificada para manejar resultados de operaciones
 * y errores en la capa de datos, permitiendo un manejo de errores consistente y type-safe.
 *
 * @author PelkiDev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.util.models

/**
 * Interfaz base sellada para representar cualquier tipo de error en la capa de datos.
 * Permite agrupar diferentes categorías de errores (red, locales, etc.) bajo un tipo común.
 * 
 * **Propósito:**
 * Esta interfaz proporciona un tipo común para todos los errores que pueden ocurrir
 * en la capa de datos, permitiendo un manejo unificado de errores.
 *
 * **Implementaciones:**
 * - [LocalModelError]: Errores de validación local o lógica de negocio
 * - [NetworkModelError]: Errores de red o comunicación con el servidor
 *
 * @author PelkiDev
 * @version 1.0.0
 */
sealed interface ModelError

/**
 * Representa errores específicos de validaciones locales o de lógica de negocio
 * que ocurren antes de una llamada de red.
 * 
 * **Propósito:**
 * Este enum define los tipos de errores que se pueden mostrar al usuario en la UI.
 * Se utiliza para determinar qué tipo de mensaje o acción debe mostrarse al usuario
 * cuando ocurre un error.
 *
 * **Uso:**
 * Este enum se utiliza principalmente en la capa de presentación para determinar
 * cómo mostrar los errores al usuario. Se obtiene mediante la conversión de [ModelError]
 * usando [com.mx.liftechnology.data.util.convertToUI] o [com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper].
 *
 * @author PelkiDev
 * @version 1.0.0
 */
enum class UserError {
    /** ModelError que indica que uno o más campos requeridos para la operación están vacíos o son inválidos. */
    SHOW_GENERIC_ERROR,
    SHOW_SPECIFIC_ERROR,
    SHOW_INCOMPLETE_ERROR,
    UNAUTHORIZED,
    LOGS,
    USER_NOT_ACTIVE,
    NO_INTERNET,
    WITHOUT_ACCESS
}


/**
 * Representa errores específicos de validaciones locales o de lógica de negocio
 * que ocurren antes de una llamada de red o durante el procesamiento local de datos.
 * 
 * **Propósito:**
 * Este enum define los errores que ocurren en la capa de datos antes de realizar
 * una llamada de red, como validaciones de datos incompletos o errores en el procesamiento local.
 *
 * **Valores:**
 * - `USER_INCOMPLETE_DATA`: Indica que uno o más campos requeridos están vacíos o son inválidos.
 * - `RESPONSE_INCOMPLETE_DATA`: Indica que la respuesta del servidor está incompleta o mal formada.
 * - `CATCH`: Indica que se capturó una excepción no esperada durante el procesamiento local.
 * - `EMPTY`: Indica que se esperaban datos pero se recibió una respuesta vacía.
 *
 * @author PelkiDev
 * @version 1.0.0
 */
enum class LocalModelError : ModelError {
    /** Indica que uno o más campos requeridos para la operación están vacíos o son inválidos. */
    USER_INCOMPLETE_DATA,
    /** Indica que la respuesta del servidor está incompleta o mal formada. */
    RESPONSE_INCOMPLETE_DATA,
    /** Indica que se capturó una excepción no esperada durante el procesamiento local. */
    CATCH,
    /** Indica que se esperaban datos pero se recibió una respuesta vacía. */
    EMPTY
}


/**
 * Representa errores que ocurren durante la comunicación con el servidor o la red.
 * 
 * **Propósito:**
 * Este enum define los errores que pueden ocurrir durante las operaciones de red,
 * incluyendo errores HTTP, problemas de conectividad y errores del servidor.
 *
 * **Valores principales:**
 * - `TIMEOUT`: La petición excedió el tiempo de espera.
 * - `NO_INTERNET`: No hay conexión a Internet disponible.
 * - `UNAUTHORIZED` (401): El usuario no está autenticado o el token es inválido.
 * - `NOT_FOUND` (404): El recurso solicitado no existe.
 * - `BAD_REQUEST` (400): La petición es inválida.
 * - `CONFLICT` (409): Hay un conflicto con el estado actual del recurso.
 * - `SERVER_ERROR` (500): Error interno del servidor.
 * - `WITHOUT_ACCESS` (403): El usuario no tiene permisos para acceder al recurso.
 * - `NOT_ACTIVE` (430): El usuario o recurso no está activo.
 * - `TOO_MANY_REQUESTS` (429): Se han realizado demasiadas peticiones.
 * - `SERIALIZATION`: Error al serializar o deserializar datos.
 * - `UNKNOWN`: Error desconocido no categorizado.
 * - `UNKNOWN_REGISTER`: Error desconocido durante el registro.
 * - `EMPTY`: La respuesta está vacía cuando se esperaban datos.
 *
 * @see com.mx.liftechnology.data.util.NetworkException Para ver cómo se mapean las excepciones a estos errores.
 *
 * @author PelkiDev
 * @version 1.0.0
 */
enum class NetworkModelError : ModelError {
    /** La petición excedió el tiempo de espera. */
    TIMEOUT,
    /** Se han realizado demasiadas peticiones (429). */
    TOO_MANY_REQUESTS,
    /** No hay conexión a Internet disponible. */
    NO_INTERNET,
    /** Error interno del servidor (500). */
    SERVER_ERROR,
    /** Error al serializar o deserializar datos. */
    SERIALIZATION,
    /** El usuario no está autenticado o el token es inválido (401). */
    UNAUTHORIZED,
    /** El recurso solicitado no existe (404). */
    NOT_FOUND,
    /** Hay un conflicto con el estado actual del recurso (409). */
    CONFLICT,
    /** La petición es inválida (400). */
    BAD_REQUEST,
    /** Error desconocido no categorizado. */
    UNKNOWN,
    /** Error desconocido durante el registro. */
    UNKNOWN_REGISTER,
    /** La respuesta está vacía cuando se esperaban datos. */
    EMPTY,
    /** El usuario o recurso no está activo (430). */
    NOT_ACTIVE,
    /** El usuario no tiene permisos para acceder al recurso (403). */
    WITHOUT_ACCESS
}

/**
 * Clase sellada que encapsula el resultado de una operación en la capa de datos.
 * Puede ser un éxito ([SuccessResult]) o un error ([ErrorResult]).
 *
 * **Propósito:**
 * Esta clase proporciona una forma type-safe de manejar resultados de operaciones
 * que pueden fallar, eliminando la necesidad de usar excepciones o valores nulos
 * para representar errores.
 *
 * **Uso:**
 * ```kotlin
 * when (val result = repository.someOperation()) {
 *     is SuccessResult -> {
 *         // Manejar éxito
 *         val data = result.data
 *     }
 *     is ErrorResult -> {
 *         // Manejar error
 *         val error = result.error
 *     }
 * }
 * ```
 *
 * **Ventajas:**
 * - Type-safe: El compilador fuerza el manejo de ambos casos (éxito y error)
 * - Sin excepciones: Los errores se manejan como valores, no como excepciones
 * - Composable: Fácil de combinar con otras operaciones
 *
 * @param D El tipo de dato en caso de éxito.
 * @param E El tipo de error, que debe implementar la interfaz [ModelError].
 *
 * @see SuccessResult Para el caso de éxito.
 * @see ErrorResult Para el caso de error.
 *
 * @author PelkiDev
 * @version 1.0.0
 */
sealed class ModelResult<out D, out E: ModelError>

/**
 * Representa un resultado exitoso, conteniendo los datos de la operación.
 *
 * **Uso:**
 * ```kotlin
 * val result: ModelResult<String, NetworkModelError> = SuccessResult("Operación exitosa")
 * when (result) {
 *     is SuccessResult -> println(result.data) // "Operación exitosa"
 *     is ErrorResult -> println("Error: ${result.error}")
 * }
 * ```
 *
 * @param data Los datos resultantes de la operación exitosa.
 *
 * @author PelkiDev
 * @version 1.0.0
 */
data class SuccessResult<out D>(val data: D) : ModelResult<D, Nothing>()

/**
 * Representa un resultado fallido, conteniendo el error específico.
 *
 * **Uso:**
 * ```kotlin
 * val result: ModelResult<String, NetworkModelError> = ErrorResult(NetworkModelError.NOT_FOUND)
 * when (result) {
 *     is SuccessResult -> println(result.data)
 *     is ErrorResult -> println("Error: ${result.error}") // Error: NOT_FOUND
 * }
 * ```
 *
 * @param error El error que ocurrió durante la operación. Debe implementar [ModelError].
 *
 * @author PelkiDev
 * @version 1.0.0
 */
data class ErrorResult<out E: ModelError>(val error: E) : ModelResult<Nothing, E>()
