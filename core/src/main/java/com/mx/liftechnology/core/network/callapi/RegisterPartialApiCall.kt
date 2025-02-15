package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RegisterPartialApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_PARTIAL)
    suspend fun callApi(
        @Body credentials: CredentialsRegisterPartial
    ): Response<GenericResponse<List<String?>?>>
}

// Modelo para credenciales
data class CredentialsRegisterPartial(
    @SerializedName("numparciales")
    val numparciales: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val profesorescuelaciclogrupo_id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("profesor_id")
    val profesor_id: Int?,
    @SerializedName("parciales")
    val parciales: List<Partials?>
)

data class Partials(
    @SerializedName("descripcion")
    val descripcion: String?,
    @SerializedName("fechainicio")
    val fechainicio: String?,
    @SerializedName("fechafinal")
    val fechafinal: String?
)