package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the subject list API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListSubjectApiCall {
    /**
     * Makes the API request to get the list of subjects.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of [ResponseGetListSubject] data.
     */
    @POST(Environment.END_POINT_GET_SUBJECT)
    suspend fun callApi(
        @Body request: RequestGetListSubject
    ): Response<ResponseGeneric<List<ResponseGetListSubject?>?>>
}

/**
 * Data model for the subject list request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetListSubject(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?
)

/**
 * Data model for the subject response.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListSubject(
    @SerializedName("campoformativopecg_id")
    val subjectTeacherSchoolCycleGroupId: Int?,
    @SerializedName("cf_descripcion")
    val subjectDescription: String?
)