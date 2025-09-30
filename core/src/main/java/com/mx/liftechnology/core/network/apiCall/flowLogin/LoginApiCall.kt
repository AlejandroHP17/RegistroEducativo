package com.mx.liftechnology.core.network.apiCall.flowLogin

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

fun interface LoginApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_LOGIN)
    suspend fun callApi(
        @Body request: RequestLogin
    ): Response<ResponseGeneric<ResponseLogin>?>
}

// Modelo para credenciales
data class RequestLogin(
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

data class ResponseLogin(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("expires_in")
    val expiresToken: Int,
    @SerializedName("token_type")
    val typeToken: String?,
    @SerializedName("user")
    val userLogin: UserLogin?
)

data class UserLogin(
    @SerializedName("name")
    val name: String?,
    @SerializedName("paterno")
    val lastName: String?,
    @SerializedName("materno")
    val secondLastName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("alumno_id")
    val studentId: Int?,
    @SerializedName("role")
    val role: String?,
)
