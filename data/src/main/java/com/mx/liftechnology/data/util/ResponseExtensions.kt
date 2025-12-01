/**
 * @file Proporciona funciones de extensión para simplificar el manejo de respuestas de Retrofit.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.util

import com.mx.liftechnology.core.model.ResponseGeneric
import retrofit2.HttpException
import retrofit2.Response

/**
 * Función de extensión que ejecuta una llamada de Retrofit y maneja automáticamente
 * los errores y el mapeo de datos, simplificando el código repetitivo en los repositorios.
 *
 * **Uso:**
 * ```kotlin
 * override suspend fun login(...): ModelResult<LoginData, NetworkModelError> {
 *     val request = RequestLogin(...)
 *     return authApi.login(request).executeOrError { it.toData() }
 * }
 * ```
 *
 * **Comportamiento:**
 * - Si la respuesta es exitosa y contiene datos, aplica el mapper y retorna `SuccessResult`
 * - Si la respuesta es exitosa pero no contiene datos, retorna `ErrorResult` con `NetworkModelError.EMPTY`
 * - Si la respuesta no es exitosa, retorna `ErrorResult` con el error HTTP correspondiente
 * - Si ocurre una excepción, retorna `ErrorResult` con el error de red correspondiente
 *
 * @param mapper Función que transforma el tipo de datos de la respuesta (`T`) al tipo de dominio (`R`).
 * @return Un [ModelResult] que contiene el resultado mapeado en caso de éxito, o un error en caso de fallo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
suspend fun <T, R> Response<ResponseGeneric<T>>.executeOrError(
    mapper: (T) -> R?
): ModelResult<R, NetworkModelError> {
    return try {
        if (isSuccessful) {
            val body = body()
            val data = body?.data
            if (data != null) {
                val mappedResult = mapper(data)
                if (mappedResult != null) {
                    SuccessResult(mappedResult)
                } else {
                    ErrorResult(NetworkModelError.EMPTY)
                }
            } else {
                ErrorResult(NetworkModelError.EMPTY)
            }
        } else {
            ErrorResult(NetworkException.handleException(HttpException(this)))
        }
    } catch (e: Exception) {
        ErrorResult(NetworkException.handleException(e))
    }
}

/**
 * Función de extensión para respuestas que no tienen un wrapper genérico.
 * Útil para respuestas simples que retornan directamente el tipo de datos.
 *
 * @param mapper Función que transforma el tipo de datos de la respuesta (`T`) al tipo de dominio (`R`).
 * @return Un [ModelResult] que contiene el resultado mapeado en caso de éxito, o un error en caso de fallo.
 */
suspend fun <T, R> Response<T>.executeOrErrorDirect(
    mapper: (T) -> R?
): ModelResult<R, NetworkModelError> {
    return try {
        if (isSuccessful) {
            val body = body()
            if (body != null) {
                val mappedResult = mapper(body)
                if (mappedResult != null) {
                    SuccessResult(mappedResult)
                } else {
                    ErrorResult(NetworkModelError.EMPTY)
                }
            } else {
                ErrorResult(NetworkModelError.EMPTY)
            }
        } else {
            ErrorResult(NetworkException.handleException(HttpException(this)))
        }
    } catch (e: Exception) {
        ErrorResult(NetworkException.handleException(e))
    }
}

