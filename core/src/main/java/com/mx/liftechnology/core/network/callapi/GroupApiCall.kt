package com.mx.liftechnology.core.network.callapi

import com.mx.liftechnology.core.model.modelApi.DataGroupTeacher
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

fun interface GroupApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_GROUP)
    suspend fun callApi(
        @Body credentials: CredentialsGroup
    ): Response<GenericResponse<List<DataGroupTeacher?>?>>
}

// Modelo para credenciales
data class CredentialsGroup(
    val profesor_id : Int?,
    val user_id : Int?
)
