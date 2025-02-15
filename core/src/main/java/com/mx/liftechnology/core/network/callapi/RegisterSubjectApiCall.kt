package com.mx.liftechnology.core.network.callapi

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
    val campoformativo: String?,
    val opciones: Int?,
    val profesorescuelaciclogrupo_id: Int?,
    val user_id : Int?,
    val profesor_id : Int?,
    val porcentajes : List<Percent?>
)

data class Percent(
    val trabajo_id: Int?,
    val porcentaje : Int?
)



data class Subjectls(
    val descripcion : String?,
    val fechainicio : String?,
    val fechafinal : String?
)