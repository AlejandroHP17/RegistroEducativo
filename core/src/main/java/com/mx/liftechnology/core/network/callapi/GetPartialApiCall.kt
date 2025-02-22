package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


fun interface GetPartialApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_PARTIAL)
    suspend fun callApi(
        @Body credentials: CredentialsGetPartial,
    ): Response<GenericResponse<List<ResponseGetPartial?>?>>
}

// Modelo para credenciales
data class CredentialsGetPartial(
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
)

data class ResponseGetPartial(
    @SerializedName("parcialciclogrupo_id")
    val partialCycleGroup: Int,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("fechainicio")
    val startDate: String,
    @SerializedName("fechafinal")
    val endDate: String,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int,
)