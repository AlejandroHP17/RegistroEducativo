package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


fun interface GetListAssessmentTypeApiCall{
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_ASSESSMENT_TYPE)
    suspend fun callApi(
        @Body credentials: CredentialsGetListAssessmentType
    ): Response<GenericResponse<List<ResponseGetListAssessmentType?>?>>
}

data class CredentialsGetListAssessmentType(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
)

data class ResponseGetListAssessmentType(
    @SerializedName("id")
    val assessmentTypeId : Int?,
    @SerializedName("descripcion")
    val description	:String?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId : Int?

)
