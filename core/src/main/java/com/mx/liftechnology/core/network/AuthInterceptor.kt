package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.network.enviroment.Environment
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * OkHttp interceptor for adding the authentication token to requests.
 *
 * @property tokenProvider The provider for the authentication token.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    /**
     * Intercepts the request and adds the token if required.
     *
     * @param chain The interceptor chain.
     * @return The response.
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
     * Checks if a request requires authentication.
     *
     * @param request The request to check.
     * @return True if the request requires authentication, false otherwise.
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
