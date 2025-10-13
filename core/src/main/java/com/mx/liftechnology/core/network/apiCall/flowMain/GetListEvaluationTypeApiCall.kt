package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the evaluation type list API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListEvaluationTypeApiCall{
    /**
     * Makes the API request to get the list of evaluation types.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of strings.
     */
    @POST(Environment.END_POINT_GET_EVALUATION_TYPE)
    suspend fun callApi(
        @Body request: RequestGetListEvaluationType
    ): Response<ResponseGeneric<List<String>?>?>
}

/**
 * Data model for the evaluation type list request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetListEvaluationType(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
)
