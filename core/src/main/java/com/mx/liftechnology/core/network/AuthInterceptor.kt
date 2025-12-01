/**
 * @file Define el interceptor de autenticación para las llamadas de red.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network

import com.google.gson.Gson
import com.mx.liftechnology.core.network.environment.Environment
import com.mx.liftechnology.core.util.session.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject

/**
 * Interceptor de OkHttp que gestiona la autenticación y el refresh de tokens de forma automática.
 * 
 * **Funcionalidad:**
 * 1. Añade el token de acceso a las cabeceras de las peticiones que requieren autenticación
 * 2. Detecta respuestas 401 (no autorizado) y automáticamente intenta refrescar el token
 * 3. Reintenta la petición original con el nuevo token si el refresh es exitoso
 * 4. Notifica la expiración de sesión si el refresh falla o no hay refresh token disponible
 * 
 * **Flujo de autenticación:**
 * - Si la petición no requiere autenticación (login, register, refresh), la deja pasar sin modificar
 * - Si requiere autenticación, añade el header "Authorization: Bearer {token}"
 * - Si recibe un 401, intenta refrescar el token usando el refresh token almacenado
 * - Si el refresh es exitoso, guarda el nuevo token y reintenta la petición original
 * - Si el refresh falla o no hay refresh token, cierra la sesión y notifica la expiración
 * 
 * **Endpoints que NO requieren autenticación:**
 * - `auth/login` - Inicio de sesión
 * - `auth/register` - Registro de usuario
 * - `auth/refresh` - Refresh de token
 * 
 * **Diferencia con otros interceptores:**
 * - [ErrorHandlingInterceptor]: Maneja errores HTTP generales (4xx, 5xx) y los loguea
 * - [ConnectionErrorInterceptor]: Diagnostica errores de conectividad de bajo nivel
 * - [AuthInterceptor]: Maneja específicamente la autenticación y el refresh de tokens
 *
 * @property sessionManager El gestor de sesión que notifica cuando la sesión expira.
 * @property tokenProvider El proveedor que suministra y gestiona los tokens de autenticación.
 * @author Pelkidev
 * @version 1.0.0
 */
class AuthInterceptor(
    private val sessionManager: SessionManager,
    private val tokenProvider: TokenProvider
) : Interceptor {

    /**
     * Intercepta la petición y añade el token de autenticación si es necesario.
     * Si la respuesta es 401, intenta refrescar el token automáticamente.
     * 
     * **Flujo:**
     * 1. Verifica si la petición requiere autenticación
     * 2. Si no requiere, la deja pasar sin modificar
     * 3. Si requiere, añade el header Authorization con el access token
     * 4. Si la respuesta es 401:
     *    - Cierra la respuesta actual
     *    - Verifica si hay refresh token disponible
     *    - Si no hay, cierra sesión y notifica expiración
     *    - Si hay, intenta refrescar el token
     *    - Si el refresh es exitoso, guarda el nuevo token y reintenta la petición original
     *    - Si el refresh falla, cierra sesión y notifica expiración
     * 
     * @param chain La cadena de interceptores.
     * @return La respuesta de la petición (puede ser la original o una respuesta de reintento).
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (!requiresAuth(request)) {
            return chain.proceed(request)
        }

        val accessToken = tokenProvider.getToken()
        val authRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(authRequest)

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
                .url(Environment.URL_BASE + Environment.END_POINT_REFRESH)
                .post(refreshBody)
                .build()

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
            }else{
                tokenProvider.closeSession()
                runBlocking { sessionManager.notifySessionExpired() }
            }

        }

        return response
    }

    /**
     * Determina si una petición requiere autenticación.
     * 
     * Las peticiones a los endpoints de login, register y refresh no requieren autenticación,
     * ya que son parte del flujo de autenticación inicial.
     * 
     * @param request La petición HTTP a verificar.
     * @return `true` si la petición requiere autenticación, `false` en caso contrario.
     */
    private fun requiresAuth(request: Request): Boolean {
        val url = request.url.toString()
        return !(
                url.endsWith(Environment.END_POINT_LOGIN) ||
                        url.endsWith(Environment.END_POINT_REGISTER) ||
                        url.endsWith(Environment.END_POINT_REFRESH)
                )
    }
}

/**
 * Data class que representa la petición de refresh de token.
 * 
 * @property refresh_token El token de refresh utilizado para obtener un nuevo access token.
 */
data class RefreshRequest(
    val refresh_token: String
)
