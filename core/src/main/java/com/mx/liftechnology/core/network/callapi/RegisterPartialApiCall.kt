package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


fun interface RegisterPartialApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_PARTIAL)
    suspend fun callApi(
        @Body credentials: CredentialsRegisterPartial
    ): Response<GenericResponse<List<String?>?>>
}

// Modelo para credenciales
data class CredentialsRegisterPartial(
    @SerializedName("numparciales")
    val numberPartials: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("parciales")
    val listPartials: List<Partials?>
)

data class Partials(
    @SerializedName("descripcion")
    val description: String?,
    @SerializedName("fechainicio")
    val startDate: String?,
    @SerializedName("fechafinal")
    val endDate: String?
)