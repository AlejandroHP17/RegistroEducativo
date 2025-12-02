package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interfaz agrupada para todas las operaciones de autenticación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface AuthApi {
    /**
     * Realiza la petición a la API para el inicio de sesión.
     */
    @POST(Environment.END_POINT_LOGIN)
    suspend fun login(@Body request: RequestLogin): Response<ResponseGeneric<ResponseLogin>>

    /**
     * Realiza la petición a la API para el registro de usuarios.
     */
    @POST(Environment.END_POINT_REGISTER)
    suspend fun register(@Body request: RequestRegisterUser): Response<ResponseGeneric<ResponseRegisterUser>>

    /**
     * Realiza la petición a la API para obtener datos de usuario.
     */
    @GET(Environment.END_POINT_GET_DATA)
    suspend fun getUserData(): Response<ResponseGeneric<ResponseDataUser>>
}

/**
 * Sección para el login
 * */
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

data class ResponseLogin(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String?,
)

/**
 * Sección para el registro de usuario
 * */
data class RequestRegisterUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("access_code")
    val activationCode: String
)

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

/**
 * Sección para obtener los datos del usuario
 * */
data class ResponseDataUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val name: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("access_level_id")
    val accessLevelId: Int?,
    @SerializedName("access_code_id")
    val accessCodeId: Int,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("id")
    val userId: Int,
    @SerializedName("created_at")
    val createdAt: String,
)