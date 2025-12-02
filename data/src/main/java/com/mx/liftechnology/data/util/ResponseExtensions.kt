/**
 * @file Proporciona funciones de extensión para simplificar el manejo de respuestas de Retrofit.
 * 
 * Este archivo centraliza el manejo de errores y el mapeo de respuestas de Retrofit,
 * eliminando código repetitivo en los repositorios y proporcionando un manejo consistente de errores.
 * 
 * **Problema resuelto:**
 * Antes, cada repositorio tenía código duplicado para manejar errores. Estas funciones de extensión
 * centralizan esa lógica, haciendo el código más mantenible y consistente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.util

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import retrofit2.HttpException
import retrofit2.Response

/**
 * Función de extensión que ejecuta una llamada de Retrofit y maneja automáticamente
 * los errores y el mapeo de datos, simplificando el código repetitivo en los repositorios.
 *
 * Esta función está diseñada para trabajar con respuestas que tienen un wrapper genérico
 * `ResponseGeneric<T>`, que es el formato estándar de las respuestas de la API.
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
 * 1. Si la respuesta es exitosa (`isSuccessful == true`) y contiene datos:
 *    - Extrae los datos del wrapper `ResponseGeneric<T>`
 *    - Aplica la función `mapper` para transformar los datos
 *    - Retorna `SuccessResult` con los datos mapeados
 * 2. Si la respuesta es exitosa pero no contiene datos o el mapper retorna null:
 *    - Retorna `ErrorResult` con `NetworkModelError.EMPTY`
 * 3. Si la respuesta no es exitosa (código HTTP de error):
 *    - Usa [NetworkException.handleException] para convertir el código HTTP a un `NetworkModelError`
 *    - Retorna `ErrorResult` con el error correspondiente
 * 4. Si ocurre una excepción durante la ejecución:
 *    - Captura la excepción y usa [NetworkException.handleException] para convertirla
 *    - Retorna `ErrorResult` con el error correspondiente
 *
 * **Ventajas:**
 * - Elimina código repetitivo de manejo de errores en repositorios
 * - Proporciona manejo consistente de errores en toda la aplicación
 * - Maneja automáticamente casos de nulos y respuestas vacías
 * - Integra el mapeo de datos en el flujo de manejo de errores
 *
 * @param mapper Función que transforma el tipo de datos de la respuesta (`T`) al tipo de dominio (`R`).
 *               Puede retornar `null` si los datos no son válidos, lo que resultará en un error `EMPTY`.
 * @return Un [com.mx.liftechnology.core.util.models.ModelResult] que contiene el resultado mapeado en caso de éxito, o un error en caso de fallo.
 *
 * @see NetworkException Para ver cómo se manejan las excepciones.
 * @see executeOrErrorDirect Para respuestas sin wrapper genérico.
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
 * Esta función es similar a [executeOrError], pero está diseñada para respuestas que
 * no usan el wrapper `ResponseGeneric<T>`, retornando directamente el tipo de datos.
 *
 * **Uso:**
 * ```kotlin
 * override suspend fun getData(): ModelResult<DataModel, NetworkModelError> {
 *     return api.getData().executeOrErrorDirect { it.toData() }
 * }
 * ```
 *
 * **Comportamiento:**
 * Similar a [executeOrError], pero trabaja directamente con `Response<T>` en lugar de `Response<ResponseGeneric<T>>`.
 * - Si la respuesta es exitosa y contiene datos, aplica el mapper y retorna `SuccessResult`
 * - Si la respuesta es exitosa pero no contiene datos o el mapper retorna null, retorna `ErrorResult` con `EMPTY`
 * - Si la respuesta no es exitosa o ocurre una excepción, retorna `ErrorResult` con el error correspondiente
 *
 * @param mapper Función que transforma el tipo de datos de la respuesta (`T`) al tipo de dominio (`R`).
 *               Puede retornar `null` si los datos no son válidos.
 * @return Un [ModelResult] que contiene el resultado mapeado en caso de éxito, o un error en caso de fallo.
 *
 * @see executeOrError Para respuestas con wrapper genérico `ResponseGeneric<T>`.
 * @see NetworkException Para ver cómo se manejan las excepciones.
 *
 * @author Pelkidev
 * @version 1.0.0
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

