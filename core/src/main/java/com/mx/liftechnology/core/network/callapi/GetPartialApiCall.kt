package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface GetPartialApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_PARTIAL)
    suspend fun callApi(
        @Body credentials: CredentialsGetPartial,
    ): Response<GenericResponse<List<ResponseGetPartial?>?>>
}

// Modelo para credenciales
data class CredentialsGetPartial(
    @SerializedName("profesorescuelaciclogrupo_id")
    val profesorescuelaciclogrupo_id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("profesor_id")
    val profesor_id: Int?,
)

data class ResponseGetPartial(
    @SerializedName("parcialciclogrupo_id")
    val parcialciclogrupo_id: Int,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("fechainicio")
    val fechainicio: String,
    @SerializedName("fechafinal")
    val fechafinal: String,
    @SerializedName("profesorescuelaciclogrupo_id")
    val profesorescuelaciclogrupo_id: Int,
)