/**
 * @file Define el interceptor de autenticación para las llamadas de red.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.network.environment.Environment
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Interceptor de OkHttp que añade el token de autenticación a las cabeceras de las peticiones que lo requieran.
 *
 * @property tokenProvider El proveedor que suministra el token de autenticación.
 * @author Pelkidev
 * @version 1.0.0
 */
class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    /**
     * Intercepta la petición y añade la cabecera de autenticación si es necesario.
     *
     * @param chain La cadena de interceptores.
     * @return La respuesta de la petición.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (requiresAuth(request)) {
            val token = tokenProvider.getToken() ?: ""
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(newRequest)
        }
        return chain.proceed(request)
    }

    /**
     * Comprueba si una petición requiere autenticación, basándose en su URL.
     *
     * @param request La petición a comprobar.
     * @return `true` si la petición requiere autenticación, `false` en caso contrario.
     */
    private fun requiresAuth(request: Request): Boolean {
        return when (request.url.toString()) {
            Environment.URL_BASE + Environment.END_POINT_LOGIN,
            Environment.URL_BASE + Environment.END_POINT_REGISTER
            -> false

            else -> true
        }
    }
}
