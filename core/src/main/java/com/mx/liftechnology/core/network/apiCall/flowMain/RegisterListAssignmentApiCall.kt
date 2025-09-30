package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


fun interface RegisterListAssignmentApiCall{
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_ASSIGNMENT)
    suspend fun callApi(
        @Body request: RequestRegisterAssignment
    ): Response<ResponseGeneric<List<String>?>?>
}

data class RequestRegisterAssignment(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
)
