package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.network.enviroment.Environment
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        /** Add token if the endpoint needs it*/
        if (requiresAuth(request)) {
            val token = tokenProvider.getToken() ?: ""
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(newRequest)
        }
        return chain.proceed(request)
    }

    /** Validate with the end point
     * @author pelkidev
     * @since 1.0.0
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

