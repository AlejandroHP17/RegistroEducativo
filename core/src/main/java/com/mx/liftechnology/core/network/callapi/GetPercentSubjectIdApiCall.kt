package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetPercentSubjectIdApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_SUBJECT_PERCENT)
    suspend fun callApi(
        @Body credentials: CredentialsGetPercentSubjectId
    ): Response<GenericResponse<List<ResponseGetPercentSubjectId>?>?>
}

data class CredentialsGetPercentSubjectId(
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