/**
 * @file Define la llamada a la API para el inicio de sesión y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowLogin

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz para la llamada a la API de inicio de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface LoginApiCall {
    /**
     * Realiza la petición a la API para el inicio de sesión.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con los datos de [ResponseLogin].
     */
    @POST(Environment.END_POINT_LOGIN)
    suspend fun callApi(
        @Body request: RequestLogin
    ): Response<ResponseGeneric<ResponseLogin?>>
}

/**
 * Modelo de datos para la petición de inicio de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestLogin(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("imei")
    val imei: String
)

/**
 * Modelo de datos para la respuesta de inicio de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseLogin(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("token_type")
    val typeToken: String?,
)