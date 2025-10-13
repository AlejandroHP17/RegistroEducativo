package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the student registration API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterStudentApiCall {
    /**
     * Makes the API request to register a student.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of strings.
     */
    @POST(Environment.END_POINT_REGISTER_STUDENT)
    suspend fun callApi(
        @Body request: RequestRegisterStudent
    ): Response<ResponseGeneric<List<String?>?>>
}

/**
 * Data model for the student registration request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterStudent(
    @SerializedName("nombres")
    val name: String,
    @SerializedName("paterno")
    val lastName: String,
    @SerializedName("materno")
    val secondLastName: String,
    @SerializedName("curp")
    val curp: String,
    @SerializedName("fechanacimiento")
    val birthday: String,
    @SerializedName("celular")
    val phoneNumber: String,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
)