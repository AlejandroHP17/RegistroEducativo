package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the assignment list registration API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListAssignmentApiCall{
    /**
     * Makes the API request to register a list of assignments.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of strings.
     */
    @POST(Environment.END_POINT_REGISTER_ASSIGNMENT)
    suspend fun callApi(
        @Body request: RequestRegisterAssignment
    ): Response<ResponseGeneric<List<String>?>?>
}

/**
 * Data model for the assignment list registration request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterAssignment(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
)
