package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the student list API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListStudentApiCall {
    /**
     * Makes the API request to get the list of students.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of [ResponseGetStudent] data.
     */
    @POST(Environment.END_POINT_GET_STUDENT)
    suspend fun callApi(
        @Body request: RequestGetListStudent
    ): Response<ResponseGeneric<List<ResponseGetStudent?>?>>
}

/**
 * Data model for the student list request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetListStudent(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?
)

/**
 * Data model for the student response.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetStudent(
    @SerializedName("id")
    val id: String?,
    @SerializedName("alumno_id")
    val studentId: String?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: String?,
    @SerializedName("curp")
    val curp: String?,
    @SerializedName("fechanacimiento")
    val birthday: String?,
    @SerializedName("celular")
    val phoneNumber: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("paterno")
    val lastName: String?,
    @SerializedName("materno")
    val secondLastName: String?
)