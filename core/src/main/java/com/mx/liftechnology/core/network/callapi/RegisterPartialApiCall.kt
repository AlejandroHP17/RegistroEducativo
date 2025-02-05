package com.mx.liftechnology.core.network.callapi

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
    val numparciales: Int?,
    val profesorescuelaciclogrupo_id: Int?,
    val user_id : Int?,
    val profesor_id : Int?,
    val parciales : List<Partials?>
)

data class Partials(
    val descripcion : String?,
    val fechainicio : String?,
    val fechafinal : String?
)