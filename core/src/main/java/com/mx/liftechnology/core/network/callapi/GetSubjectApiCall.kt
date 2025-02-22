package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

fun interface GetListSubjectApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_SUBJECT)
    suspend fun callApi(
        @Body credentials: CredentialGetListSubject
    ): Response<GenericResponse<List<String?>?>>
}

data class CredentialGetListSubject(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?
)