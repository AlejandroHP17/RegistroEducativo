package com.mx.liftechnology.core.network.callapi

import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RegisterSchoolApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_SCHOOL)
    suspend fun callApi(
        @Body credentials: CredentialsRegisterSchool
    ): Response<GenericResponse<List<String?>?>>
}

// Modelo para credenciales
data class CredentialsRegisterSchool(
    val cct: String?,
    val tipocicloescolar_id: Int?,
    val grado : Int?,
    val nombregrupo : String?,
    val anio : String?,
    val periodo : Int?,
    val profesor_id : Int?,
    val user_id : Int?,
)