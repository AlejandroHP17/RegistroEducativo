package com.mx.liftechnology.core.network.callapi

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetListSubjectApiCall {
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_SUBJECT)
    suspend fun callApi(
        @Body credentials: CredentialGetListSubject
    ): Response<GenericResponse<List<String?>?>>
}

data class CredentialGetListSubject(
    @SerializedName("profesor_id")
    val profesor_id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val profesorescuelaciclogrupo_id: Int?
)

data class ResponseGetSubject(
    val id: String?,

    )