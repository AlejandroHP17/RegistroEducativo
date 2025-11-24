/**
 * @file Define el interceptor de autenticación para las llamadas de red.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network

import com.google.gson.Gson
import com.mx.liftechnology.core.network.environment.Environment
import com.mx.liftechnology.core.network.environment.Environment.END_POINT_REFRESH
import com.mx.liftechnology.core.util.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject

/**
 * Interceptor de OkHttp que añade el token de autenticación a las cabeceras de las peticiones que lo requieran.
 *
 * @property tokenProvider El proveedor que suministra el token de autenticación.
 * @author Pelkidev
 * @version 1.0.0
 */
class AuthInterceptor(
    private val sessionManager: SessionManager,
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Evitamos interceptar login y refresh
        if (!requiresAuth(request)) {
            return chain.proceed(request)
        }

        // 1. Agregar access token actual
        val accessToken = tokenProvider.getToken()
        val authRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(authRequest)

        // 2. Si el access token expiró → intentar refrescar
        if (response.code == 401) {
            response.close()

            val refreshToken = tokenProvider.getRefreshToken()
            if (refreshToken.isNullOrEmpty()) {
                tokenProvider.closeSession()
                runBlocking { sessionManager.notifySessionExpired() }
                return response
            }

            val refreshJson = Gson().toJson(RefreshRequest(refreshToken))
            val refreshBody = refreshJson.toRequestBody("application/json".toMediaType())

            val refreshRequest = Request.Builder()
                .url(Environment.URL_BASE + END_POINT_REFRESH)
                .post(refreshBody)
                .build()

            // 🔥 Ejecutamos la petición usando el propio interceptor
            val refreshResponse = chain.proceed(refreshRequest)

            if (refreshResponse.isSuccessful) {

                val rawBody = refreshResponse.peekBody(Long.MAX_VALUE).string()
                val root = JSONObject(rawBody)
                val data = root.getJSONObject("data")
                val newAccess = data.getString("access_token")

                tokenProvider.saveNewToken(newAccess)

                refreshResponse.close()

                val retryRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer $newAccess")
                    .build()

                return chain.proceed(retryRequest)
            }

        }

        return response
    }

    private fun requiresAuth(request: Request): Boolean {
        val url = request.url.toString()
        return !(
                url.endsWith(Environment.END_POINT_LOGIN) ||
                        url.endsWith(Environment.END_POINT_REGISTER) ||
                        url.endsWith(Environment.END_POINT_REFRESH)
                )
    }
}

data class RefreshRequest(
    val refresh_token: String
)
