package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


fun interface RegisterStudentApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_STUDENT)
    suspend fun callApi(
        @Body request: RequestRegisterStudent
    ): Response<ResponseGeneric<List<String?>?>>
}

// Modelo para credenciales
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