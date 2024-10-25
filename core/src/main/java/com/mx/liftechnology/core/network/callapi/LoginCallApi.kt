package com.mx.liftechnology.core.network.callapi

import com.mx.liftechnology.core.model.ModelApi.Data
import com.mx.liftechnology.core.network.enviroment.Environment
import com.mx.liftechnology.core.util.GenericResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginCallApi {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT)
    suspend fun callApi(
        @Body credentials: Credentials
    ): Response<GenericResponse<Data>?>
}

// Modelo para credenciales
data class Credentials(
    val email: String,
    val pass: String
)
