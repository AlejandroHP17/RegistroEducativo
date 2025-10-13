package com.mx.liftechnology.core.network.apiCall.flowLogin

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the user registration API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterUserApiCall {
    /**
     * Makes the API request for user registration.
     *
     * @param request The user registration request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of strings.
     */
    @POST(Environment.END_POINT_REGISTER)
    suspend fun callApi(
        @Body request: RequestRegisterUser
    ): Response<ResponseGeneric<List<String>?>?>
}

/**
 * Data model for the user registration request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("codigoactivacion")
    val activationCode: String
)