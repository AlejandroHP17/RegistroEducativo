package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

fun interface GetListStudentApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_STUDENT)
    suspend fun callApi(
        @Body credentials: CredentialGetListStudent
    ): Response<GenericResponse<List<ResponseGetStudent?>?>>
}

data class CredentialGetListStudent(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?
)

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