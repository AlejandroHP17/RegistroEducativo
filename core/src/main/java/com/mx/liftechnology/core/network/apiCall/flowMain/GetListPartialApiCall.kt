/**
 * @file Define la llamada a la API para obtener la lista de parciales y los modelos de datos asociados.
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
 * Interfaz para la llamada a la API de obtención de la lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListPartialApiCall {
    /**
     * Realiza la petición a la API para obtener la lista de parciales.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGetPartial].
     */
    @POST(Environment.END_POINT_GET_PARTIAL)
    suspend fun callApi(
        @Body request: RequestGetPartial,
    ): Response<ResponseGeneric<List<ResponseGetPartial?>?>>
}

/**
 * Modelo de datos para la petición de la lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetPartial(
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("profesor_id")
    val teacherId: Int,
)

/**
 * Modelo de datos para la respuesta de la lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetPartial(
    @SerializedName("parcialciclogrupo_id")
    val partialCycleGroupId: Int?,
    @SerializedName("descripcion")
    val description: String?,
    @SerializedName("fechainicio")
    val startDate: String?,
    @SerializedName("fechafinal")
    val endDate: String?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
)