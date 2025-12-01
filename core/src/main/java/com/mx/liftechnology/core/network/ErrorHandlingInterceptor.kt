/**
 * @file Define el interceptor de manejo centralizado de errores HTTP.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.util.extension.logInfo
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Interceptor de OkHttp que maneja errores HTTP de forma centralizada.
 * Loguea información detallada de las respuestas no exitosas y categoriza los errores
 * según su código HTTP para facilitar el diagnóstico y el manejo consistente de errores.
 *
 * Este interceptor complementa al [ConnectionErrorInterceptor] que maneja errores de conexión,
 * y al [AuthInterceptor] que maneja específicamente el error 401 (no autorizado).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ErrorHandlingInterceptor : Interceptor {

    /**
     * Intercepta la respuesta y maneja los errores HTTP de forma centralizada.
     * Si la respuesta no es exitosa, loguea información detallada del error.
     *
     * @param chain La cadena de interceptores.
     * @return La respuesta de la petición (sin modificar).
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        
        return try {
            val response = chain.proceed(request)
            
            // Manejar respuestas no exitosas
            if (!response.isSuccessful) {
                handleErrorResponse(request, response)
            }
            
            response
        } catch (e: IOException) {
            // Los errores de conexión son manejados por ConnectionErrorInterceptor
            // Este interceptor se enfoca en errores HTTP (respuestas no exitosas)
            throw e
        }
    }

    /**
     * Maneja una respuesta de error HTTP, logueando información detallada.
     *
     * @param request La petición que generó el error.
     * @param response La respuesta con el error.
     */
    private fun handleErrorResponse(request: Request, response: Response) {
        val errorCode = response.code
        val errorMessage = response.message
        val errorCategory = categorizeError(errorCode)
        
        // Loguear información del error
        logInfo("Error HTTP detectado:")
        logInfo("  URL: ${request.url}")
        logInfo("  Método: ${request.method}")
        logInfo("  Código: $errorCode")
        logInfo("  Mensaje: $errorMessage")
        logInfo("  Categoría: $errorCategory")
        
        // Intentar leer el cuerpo del error si está disponible
        try {
            val errorBody = response.peekBody(1024).string()
            if (errorBody.isNotBlank()) {
                logInfo("  Cuerpo del error: $errorBody")
            }
        } catch (e: Exception) {
            logInfo("  No se pudo leer el cuerpo del error: ${e.message}")
        }
    }

    /**
     * Categoriza un código de error HTTP según su tipo.
     *
     * @param code El código de estado HTTP.
     * @return Una descripción de la categoría del error.
     */
    private fun categorizeError(code: Int): String {
        return when (code) {
            in 400..499 -> {
                when (code) {
                    400 -> "BAD_REQUEST - Solicitud incorrecta"
                    401 -> "UNAUTHORIZED - No autorizado (manejado por AuthInterceptor)"
                    403 -> "FORBIDDEN - Sin acceso"
                    404 -> "NOT_FOUND - Recurso no encontrado"
                    409 -> "CONFLICT - Conflicto"
                    429 -> "TOO_MANY_REQUESTS - Demasiadas peticiones"
                    430 -> "NOT_ACTIVE - Usuario no activo"
                    else -> "CLIENT_ERROR - Error del cliente (4xx)"
                }
            }
            in 500..599 -> {
                when (code) {
                    500 -> "INTERNAL_SERVER_ERROR - Error interno del servidor"
                    502 -> "BAD_GATEWAY - Error de puerta de enlace"
                    503 -> "SERVICE_UNAVAILABLE - Servicio no disponible"
                    504 -> "GATEWAY_TIMEOUT - Timeout de puerta de enlace"
                    else -> "SERVER_ERROR - Error del servidor (5xx)"
                }
            }
            else -> "UNKNOWN_ERROR - Error desconocido"
        }
    }
}

