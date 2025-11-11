/**
 * @file Define la llamada a la API para el registro de usuarios y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.auth

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz para la llamada a la API de registro de usuarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterUserApiCall {
    /**
     * Realiza la petición a la API para el registro de usuarios.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @POST(Environment.END_POINT_REGISTER)
    suspend fun callApi(
        @Body request: RequestRegisterUser
    ): Response<ResponseGeneric<ResponseRegisterUser>>
}

/**
 * Modelo de datos para la petición de registro de usuarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("access_code")
    val activationCode: String
)

/**
 * Modelo de datos para la respuesta de inicio de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseRegisterUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("access_level_id")
    val accessLevel: Int,
    @SerializedName("access_code_id")
    val accessCode: Int?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("created_at")
    val createdAt: String
)