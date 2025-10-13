package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the subject percentage API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface GetPercentSubjectIdApiCall {
    /**
     * Makes the API request to get the percentage of a subject.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of [ResponseGetPercentSubjectId] data.
     */
    @POST(Environment.END_POINT_GET_SUBJECT_PERCENT)
    suspend fun callApi(
        @Body request: RequestGetPercentSubjectId
    ): Response<ResponseGeneric<List<ResponseGetPercentSubjectId>?>?>
}

/**
 * Data model for the subject percentage request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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

/**
 * Data model for the subject percentage response.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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