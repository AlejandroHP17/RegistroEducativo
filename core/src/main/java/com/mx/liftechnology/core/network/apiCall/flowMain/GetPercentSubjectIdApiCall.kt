package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetPercentSubjectIdApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_SUBJECT_PERCENT)
    suspend fun callApi(
        @Body request: RequestGetPercentSubjectId
    ): Response<ResponseGeneric<List<ResponseGetPercentSubjectId>?>?>
}

data class RequestGetPercentSubjectId(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("campoformativopecg_id")
    val subjectSchoolCycleGroupId: Int?
)

data class ResponseGetPercentSubjectId(
    @SerializedName("id")
    val id :Int?,
    @SerializedName("porcentaje")
    val percent:Int?,
    @SerializedName("campoformativopecg_id")
    val subjectSchoolCycleGroupId :	Int?,
    @SerializedName("cf_descripcion")
    val description	:	String?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("tt_id")
    val assignmentId: Int?,
    @SerializedName("tt_descripcion")
    val assignmentName:String?
)