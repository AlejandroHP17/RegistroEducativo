/**
 * @file Define el interceptor de errores de conexión para obtener más información de diagnóstico.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network

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
        timber.log.Timber.d("ConnectionErrorInterceptor: Intentando conectar a:")
        timber.log.Timber.d("  URL: ${request.url}")
        timber.log.Timber.d("  Host: ${request.url.host}")
        timber.log.Timber.d("  Port: ${request.url.port}")
        timber.log.Timber.d("  Método: ${request.method}")
        
        try {
            val response = chain.proceed(request)
            timber.log.Timber.d("ConnectionErrorInterceptor: Conexión exitosa")
            return response
        } catch (e: IOException) {
            // Log detallado del error de conexión
            timber.log.Timber.e(e, "ConnectionErrorInterceptor: Error de conexión")
            timber.log.Timber.e("  URL: ${request.url}")
            timber.log.Timber.e("  Host: ${request.url.host}")
            timber.log.Timber.e("  Port: ${request.url.port}")
            timber.log.Timber.e("  Método: ${request.method}")
            timber.log.Timber.e("  Mensaje de error: ${e.message}")
            timber.log.Timber.e("  Causa: ${e.cause?.message ?: "N/A"}")
            
            // Información adicional para ConnectException
            if (e is java.net.ConnectException) {
                timber.log.Timber.e("  Tipo de error: ConnectException")
                timber.log.Timber.e("  No se pudo establecer conexión con: ${request.url.host}:${request.url.port}")
                timber.log.Timber.e("  Soluciones sugeridas:")
                timber.log.Timber.e("    1. Verifica que el servidor esté corriendo en el puerto ${request.url.port}")
                timber.log.Timber.e("    2. Verifica que el firewall permita conexiones en ese puerto")
                timber.log.Timber.e("    3. Verifica que el dispositivo y el servidor estén en la misma red")
                timber.log.Timber.e("    4. Verifica que el router no tenga aislamiento de clientes habilitado")
                timber.log.Timber.e("    5. Si tu computadora está en Ethernet y el dispositivo en WiFi,")
                timber.log.Timber.e("       verifica que el router permita comunicación entre ambos")
            }
            
            throw e
        }
    }
}

