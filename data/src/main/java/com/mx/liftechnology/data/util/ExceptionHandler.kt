/**
 * @file Proporciona un manejador de excepciones para convertir errores en un formato de servicio de fallo definido.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.util

import com.mx.liftechnology.core.util.extension.logInfo

/**
 * Objeto que gestiona las excepciones y las convierte en [NetworkModelError].
 * Centraliza el manejo de errores de red y de servidor, traduciéndolos a un modelo de error unificado.
 *
 * **Propósito:**
 * Este objeto proporciona una capa de abstracción para el manejo de excepciones de red,
 * convirtiendo diferentes tipos de excepciones (IOException, HttpException, etc.) en
 * errores tipados del dominio (`NetworkModelError`).
 *
 * **Uso:**
 * Este objeto se utiliza principalmente dentro de las funciones de extensión `executeOrError`
 * y `executeOrErrorDirect` en [ResponseExtensions] para manejar errores de manera consistente
 * en todos los repositorios.
 *
 * **Ejemplo de uso:**
 * ```kotlin
 * try {
 *     val response = apiCall()
 *     // ...
 * } catch (e: Exception) {
 *     ErrorResult(NetworkException.handleException(e))
 * }
 * ```
 *
 * **Mapeo de excepciones:**
 * - `UnknownHostException` / `ConnectException` → `NO_INTERNET`
 * - `SocketTimeoutException` → `TIMEOUT`
 * - `HttpException` con código HTTP → Error específico según código (400, 401, 404, etc.)
 * - Otras excepciones → `UNKNOWN`
 *
 * @see NetworkModelError Para ver todos los tipos de errores disponibles.
 * @see ResponseExtensions Para ver cómo se usa en las funciones de extensión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object NetworkException {
    /**
     * Gestiona una [Throwable] y la convierte en un [NetworkModelError].
     *
     * Analiza el tipo de excepción y la convierte en el error de red correspondiente.
     * También registra el error en los logs para debugging.
     *
     * **Mapeo de códigos HTTP:**
     * - 400 → `BAD_REQUEST`
     * - 401 → `UNAUTHORIZED`
     * - 403 → `WITHOUT_ACCESS`
     * - 404 → `NOT_FOUND`
     * - 409 → `CONFLICT`
     * - 429 → `TOO_MANY_REQUESTS`
     * - 430 → `NOT_ACTIVE`
     * - 500 → `SERVER_ERROR`
     * - Otros → `UNKNOWN`
     *
     * @param exception La excepción a gestionar. Puede ser cualquier tipo de [Throwable].
     * @return Un [NetworkModelError] que representa la excepción gestionada.
     *
     * @author Pelkidev
     * @version 1.0.0
     */
    fun handleException(exception: Throwable): NetworkModelError {
        val error = when (exception) {
            is java.net.UnknownHostException -> NetworkModelError.NO_INTERNET  // No hay conexión a Internet
            is java.net.ConnectException -> NetworkModelError.NO_INTERNET  // No se puede establecer conexión con el servidor
            is java.net.SocketTimeoutException -> NetworkModelError.TIMEOUT // Timeout de conexión
            is retrofit2.HttpException -> {
                when (exception.code()) {
                    400 -> NetworkModelError.BAD_REQUEST
                    401 -> NetworkModelError.UNAUTHORIZED
                    403 -> NetworkModelError.WITHOUT_ACCESS
                    404 -> NetworkModelError.NOT_FOUND
                    409 -> NetworkModelError.CONFLICT
                    429 -> NetworkModelError.TOO_MANY_REQUESTS
                    430 -> NetworkModelError.NOT_ACTIVE
                    500 -> NetworkModelError.SERVER_ERROR
                    else -> NetworkModelError.UNKNOWN
                }
            }
            else -> NetworkModelError.UNKNOWN
        }
        logInfo(error.name, "falla servicio")
        return error
    }
}
