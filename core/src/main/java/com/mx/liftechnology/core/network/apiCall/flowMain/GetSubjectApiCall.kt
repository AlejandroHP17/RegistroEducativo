package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

fun interface GetListSubjectApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_SUBJECT)
    suspend fun callApi(
        @Body request: RequestGetListSubject
    ): Response<ResponseGeneric<List<ResponseGetListSubject?>?>>
}

data class RequestGetListSubject(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?
)


data class ResponseGetListSubject(
    @SerializedName("campoformativopecg_id")
    val subjectTeacherSchoolCycleGroupId: Int?,
    @SerializedName("cf_descripcion")
    val subjectDescription: String?
)