package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetListStudentApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_STUDENT)
    suspend fun callApi(
        @Body credentials: CredentialGetListStudent
    ): Response<GenericResponse<List<ResponseGetStudent?>?>>
}

data class CredentialGetListStudent(
    @SerializedName("profesor_id")
    val profesor_id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val profesorescuelaciclogrupo_id: Int?
)

data class ResponseGetStudent(
    @SerializedName("id")
    val id: String?,
    @SerializedName("alumno_id")
    val alumno_id: String?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val profesorescuelaciclogrupo_id: String?,
    @SerializedName("curp")
    val curp: String?,
    @SerializedName("fechanacimiento")
    val fechanacimiento: String?,
    @SerializedName("celular")
    val celular: String?,
    @SerializedName("user_id")
    val user_id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("paterno")
    val paterno: String?,
    @SerializedName("materno")
    val materno: String?
)