/**
 * @file Define la llamada a la API para obtener la lista de tipos de evaluación y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.evaluation

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz para la llamada a la API de obtención de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListEvaluationTypeApiCall{
    /**
     * Realiza la petición a la API para obtener la lista de tipos de evaluación.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @POST(Environment.END_POINT_GET_EVALUATION_TYPE)
    suspend fun callApi(
        @Body request: RequestGetListEvaluationType
    ): Response<ResponseGeneric<List<String>?>?>
}

/**
 * Modelo de datos para la petición de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetListEvaluationType(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
)
