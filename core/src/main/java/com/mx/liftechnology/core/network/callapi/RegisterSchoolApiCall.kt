package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
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
    @SerializedName("cct")
    val cct: String?,
    @SerializedName("tipocicloescolar_id")
    val tipocicloescolar_id: Int?,
    @SerializedName("grado")
    val grado: Int?,
    @SerializedName("nombregrupo")
    val nombregrupo: String?,
    @SerializedName("anio")
    val anio: String?,
    @SerializedName("periodo")
    val periodo: Int?,
    @SerializedName("profesor_id")
    val profesor_id: Int?,
    @SerializedName("user_id")
    val user_id: Int?
)