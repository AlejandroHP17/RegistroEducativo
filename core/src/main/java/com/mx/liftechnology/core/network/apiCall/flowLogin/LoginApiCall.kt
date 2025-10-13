package com.mx.liftechnology.core.network.apiCall.flowLogin

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the login API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface LoginApiCall {
    /**
     * Makes the API request for login.
     *
     * @param request The login request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with [ResponseLogin] data.
     */
    @POST(Environment.END_POINT_LOGIN)
    suspend fun callApi(
        @Body request: RequestLogin
    ): Response<ResponseGeneric<ResponseLogin>?>
}

/**
 * Data model for the login request.
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
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("imei")
    val imei: String
)

/**
 * Data model for the login response.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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

/**
 * Data model for the user information in the login response.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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
