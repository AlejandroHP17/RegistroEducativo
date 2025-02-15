package com.mx.liftechnology.core.network.callapi

import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetListSubjectApiCall{
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_SUBJECT)
    suspend fun callApi(
        @Body credentials: CredentialGetListSubject
    ): Response<GenericResponse<List<String?>?>>
}

data class CredentialGetListSubject(
    val profesor_id : Int?,
    val user_id : Int?,
    val profesorescuelaciclogrupo_id : Int?
)

data class ResponseGetSubject (
    val id : String?,

)