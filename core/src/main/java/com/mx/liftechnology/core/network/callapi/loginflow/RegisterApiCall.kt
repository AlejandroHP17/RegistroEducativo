package com.mx.liftechnology.core.network.callapi.loginflow

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


fun interface RegisterApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER)
    suspend fun callApi(
        @Body credentials: CredentialsRegister
    ): Response<GenericResponse<List<String>?>?>
}

// Modelo para credenciales
data class CredentialsRegister(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("codigoactivacion")
    val activationCode: String
)
