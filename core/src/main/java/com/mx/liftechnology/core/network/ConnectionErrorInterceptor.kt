/**
 * @file Define el interceptor de diagnóstico de conexión para logging y monitoreo de conectividad.
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
 * Interceptor de OkHttp dedicado al diagnóstico y logging de conectividad de red.
 * 
 * **Propósito:**
 * Este interceptor se enfoca en el diagnóstico de problemas de conectividad de bajo nivel.
 * Loguea información detallada sobre las peticiones (URL, host, puerto, método) y captura
 * errores de conexión (IOException) para facilitar el debugging de problemas de red.
 * 
 * **Diferencia con otros interceptores:**
 * - [ErrorHandlingInterceptor]: Maneja errores HTTP (códigos 4xx, 5xx) de respuestas exitosas del servidor
 * - [ConnectionErrorInterceptor]: Diagnostica errores de conectividad (fallos al establecer conexión, timeouts, etc.)
 * - [AuthInterceptor]: Maneja autenticación y refresh de tokens
 * 
 * **Comportamiento:**
 * - Loguea información de diagnóstico antes de cada petición
 * - Captura y loguea errores de conexión (IOException) con detalles
 * - Relanza los errores para que sean manejados por la capa superior
 * - No modifica las respuestas ni maneja los errores, solo los registra para diagnóstico
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ConnectionErrorInterceptor : Interceptor {
    /**
     * Intercepta la petición y loguea información detallada para diagnóstico.
     * 
     * **Flujo:**
     * 1. Loguea información de la petición (URL, host, puerto, método)
     * 2. Intenta ejecutar la petición
     * 3. Si es exitosa, loguea confirmación
     * 4. Si falla con IOException, loguea detalles del error y relanza la excepción
     * 
     * **Nota:** Este interceptor no resuelve errores, solo los registra para diagnóstico.
     * Los errores se relanzan para que sean manejados por la capa de repositorio o use case.
     *
     * @param chain La cadena de interceptores.
     * @return La respuesta de la petición si es exitosa.
     * @throws IOException Si hay un error de conexión, se relanza después de loguearlo.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        try {
            val response = chain.proceed(request)
            return response
        } catch (e: IOException) {
            logInfo("  ❌ Conexión fallida")
            logInfo("  Tipo de error: ${e.javaClass.simpleName}")
            logInfo("  Mensaje: ${e.message ?: "Sin mensaje"}")
            logInfo("  Causa: ${e.cause?.message ?: "N/A"}")
            logInfo("  Stack trace: ${e.stackTrace.take(3).joinToString("\n    ") { it.toString() }}")
            throw e
        }
    }
}

