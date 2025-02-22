package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

fun interface LoginApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_LOGIN)
    suspend fun callApi(
        @Body credentials: Credentials
    ): Response<GenericResponse<ResponseDataLogin>?>
}

// Modelo para credenciales
data class Credentials(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("imei")
    val imei: String
)

data class ResponseDataLogin(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("expires_in")
    val expiresToken: Int,
    @SerializedName("token_type")
    val typeToken: String?,
    @SerializedName("user")
    val user: User?
)

data class User(
    @SerializedName("name")
    val name: String?,
    @SerializedName("paterno")
    val lastName: String?,
    @SerializedName("materno")
    val secondLastName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("user_id")
    val userID: Int?,
    @SerializedName("profesor_id")
    val teacherID: Int?,
    @SerializedName("alumno_id")
    val studentID: Int?,
    @SerializedName("role")
    val role: String?,
)
