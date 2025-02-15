package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RegisterSubjectApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_REGISTER_SUBJECT)
    suspend fun callApi(
        @Body credentials: CredentialsRegisterSubject
    ): Response<GenericResponse<List<String?>?>>
}

// Modelo para credenciales
data class CredentialsRegisterSubject(
    @SerializedName("campoformativo")
    val campoformativo: String?,
    @SerializedName("opciones")
    val opciones: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val profesorescuelaciclogrupo_id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("profesor_id")
    val profesor_id: Int?,
    @SerializedName("porcentajes")
    val porcentajes: List<Percent?>
)

data class Percent(
    @SerializedName("trabajo_id")
    val trabajo_id: Int?,
    @SerializedName("porcentaje")
    val porcentaje: Int?
)