/**
 * @file Define la llamada a la API para obtener la lista de materias y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz para la llamada a la API de obtención de la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListSubjectApiCall {
    /**
     * Realiza la petición a la API para obtener la lista de materias.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGetListSubject].
     */
    @POST(Environment.END_POINT_GET_SUBJECT)
    suspend fun callApi(
        @Body request: RequestGetListSubject
    ): Response<ResponseGeneric<List<ResponseGetListSubject?>?>>
}

/**
 * Modelo de datos para la petición de la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetListSubject(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?
)

/**
 * Modelo de datos para la respuesta de la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListSubject(
    @SerializedName("campoformativopecg_id")
    val subjectTeacherSchoolCycleGroupId: Int?,
    @SerializedName("cf_descripcion")
    val subjectDescription: String?
)