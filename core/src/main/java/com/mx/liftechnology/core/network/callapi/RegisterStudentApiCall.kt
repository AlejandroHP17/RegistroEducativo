package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RegisterStudentApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_STUDENT)
    suspend fun callApi(
        @Body credentials: CredentialsRegisterStudent
    ): Response<GenericResponse<List<String?>?>>
}

// Modelo para credenciales
data class CredentialsRegisterStudent(
    @SerializedName("name")
    val name: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("secondLastName")
    val secondLastName: String,
    @SerializedName("curp")
    val curp: String,
    @SerializedName("fechanacimiento")
    val fechanacimiento: String,
    @SerializedName("celular")
    val celular: String,
    @SerializedName("profesorescuelaciclogrupo_id")
    val profesorescuelaciclogrupo_id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("profesor_id")
    val profesor_id: Int?,
)