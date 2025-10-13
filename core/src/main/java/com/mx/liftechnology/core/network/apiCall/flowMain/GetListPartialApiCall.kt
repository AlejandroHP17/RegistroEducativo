package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the partial list API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListPartialApiCall {
    /**
     * Makes the API request to get the list of partials.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of [ResponseGetPartial] data.
     */
    @POST(Environment.END_POINT_GET_PARTIAL)
    suspend fun callApi(
        @Body request: RequestGetPartial,
    ): Response<ResponseGeneric<List<ResponseGetPartial?>?>>
}

/**
 * Data model for the partial list request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetPartial(
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
)

/**
 * Data model for the partial response.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetPartial(
    @SerializedName("parcialciclogrupo_id")
    val partialCycleGroupId: Int,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("fechainicio")
    val startDate: String,
    @SerializedName("fechafinal")
    val endDate: String,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int,
)