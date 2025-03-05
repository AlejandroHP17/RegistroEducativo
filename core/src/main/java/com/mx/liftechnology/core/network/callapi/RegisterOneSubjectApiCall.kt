package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


fun interface RegisterOneSubjectApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_SUBJECT)
    suspend fun callApi(
        @Body credentials: CredentialsRegisterSubject
    ): Response<GenericResponse<List<String?>?>>
}

// Modelo para credenciales
data class CredentialsRegisterSubject(
    @SerializedName("campoformativo")
    val subject: String?,
    @SerializedName("opciones")
    val options: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("porcentajes")
    val percents: List<Percent?>
)

data class Percent(
    @SerializedName("trabajo_id")
    val jobId: Int?,
    @SerializedName("porcentaje")
    val percent: Int?
)










