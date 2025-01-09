package com.mx.liftechnology.core.network.callapi

import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RegisterApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER)
    suspend fun callApi(
        @Body credentials: CredentialsRegister
    ): Response<GenericResponse<List<String>?>?>
}

// Modelo para credenciales
data class CredentialsRegister(
    val email: String,
    val password: String,
    val codigoactivacion : String
)
