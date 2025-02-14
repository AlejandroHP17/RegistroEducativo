package com.mx.liftechnology.core.network.callapi

import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetListStudentApiCall{
    /** Realiza la petición al API */
    @POST(Environment.END_POINT_GET_STUDENT)
    suspend fun callApi(
        @Body credentials: CredentialGetListStudent
    ): Response<GenericResponse<List<ResponseGetStudent?>?>>
}

data class CredentialGetListStudent(
    val profesor_id : Int?,
    val user_id : Int?,
    val profesorescuelaciclogrupo_id : Int?
)

data class ResponseGetStudent (
    val id : String?,
    val alumno_id : String?,
    val profesorescuelaciclogrupo_id : String?,
    val curp : String?,
    val fechanacimiento : String?,
    val celular : String?,
    val user_id : String?,
    val name : String?,
    val paterno : String?,
    val materno : String?
)