package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


fun interface RegisterOneSchoolApiCall {
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
    val typeCycleSchoolId: Int?,
    @SerializedName("grado")
    val grade: Int?,
    @SerializedName("nombregrupo")
    val nameGroup: String?,
    @SerializedName("anio")
    val year: String?,
    @SerializedName("periodo")
    val period: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?
)