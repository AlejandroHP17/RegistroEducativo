package com.mx.liftechnology.core.network.callapi

import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RegisterStudentApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_STUDENT)
    suspend fun callApi(
        @Body credentials: CredentialsRegisterStudent
    ): Response<GenericResponse<List<String?>?>>
}

// Modelo para credenciales
data class CredentialsRegisterStudent(
    val name: String,
    val lastName: String,
    val secondLastName: String,
    val curp: String,
    val fechanacimiento: String,
    val celular: String,
    val profesorescuelaciclogrupo_id: Int?,
    val user_id : Int?,
    val profesor_id : Int?,
)