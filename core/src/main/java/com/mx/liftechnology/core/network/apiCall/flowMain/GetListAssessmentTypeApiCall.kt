package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the assessment type list API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListAssessmentTypeApiCall{
    /**
     * Makes the API request to get the list of assessment types.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of [ResponseGetListAssessmentType] data.
     */
    @POST(Environment.END_POINT_GET_ASSESSMENT_TYPE)
    suspend fun callApi(
        @Body request: RequestGetListAssessmentType
    ): Response<ResponseGeneric<List<ResponseGetListAssessmentType?>?>>
}

/**
 * Data model for the assessment type list request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetListAssessmentType(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
)

/**
 * Data model for the assessment type response.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListAssessmentType(
    @SerializedName("id")
    val assessmentTypeId : Int?,
    @SerializedName("descripcion")
    val description	:String?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId : Int?
)
