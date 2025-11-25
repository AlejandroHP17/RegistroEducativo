/**
 * @file Define el interceptor de errores de conexión para obtener más información de diagnóstico.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.util.logInfo
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Interceptor de OkHttp que loguea información detallada de las peticiones y captura errores de conexión.
 * Ayuda a diagnosticar problemas de conectividad.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ConnectionErrorInterceptor : Interceptor {
    /**
     * Intercepta la petición y loguea información detallada antes de enviarla.
     * Los errores de conexión se capturan en el catch y se loguean con detalles.
     *
     * @param chain La cadena de interceptores.
     * @return La respuesta de la petición.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        
        // Log de información de la petición antes de enviarla
        logInfo("Intentando conectar a:")
        logInfo("  URL: ${request.url}")
        logInfo("  Host: ${request.url.host}")
        logInfo("  Port: ${request.url.port}")
        logInfo("  Método: ${request.method}")
        
        try {
            val response = chain.proceed(request)
            logInfo("Conexión exitosa")
            return response
        } catch (e: IOException) {
            // Log detallado del error de conexión
            logInfo("  Conexión fallida")
            logInfo("  Mensaje de error: ${e.message}")
            logInfo("  Causa: ${e.cause?.message ?: "N/A"}")
            throw e
        }
    }
}

