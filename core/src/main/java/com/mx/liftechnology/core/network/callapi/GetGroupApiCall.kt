package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
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
    ): Response<GenericResponse<List<ResponseGroupTeacher?>?>>
}

// Modelo para credenciales
data class CredentialsGroup(
    @SerializedName("profesor_id")
    val profesor_id: Int?,
    @SerializedName("user_id")
    val user_id: Int?
)

data class ResponseGroupTeacher(
    @SerializedName("cct")
    val cct: String,
    @SerializedName("cicloescolar_id")
    val schoolYearId: Int,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("escuelaciclo_id")
    val cycleSchoolId: Int,
    @SerializedName("grupo")
    val group: String,
    @SerializedName("nombre")
    val name: String,
    @SerializedName("nombreescuela")
    val nameSchool: String,
    @SerializedName("profesor_id")
    val teacherId: Int,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int,
    @SerializedName("tipo")
    val type: String,
    @SerializedName("turno")
    val shift: String
)
