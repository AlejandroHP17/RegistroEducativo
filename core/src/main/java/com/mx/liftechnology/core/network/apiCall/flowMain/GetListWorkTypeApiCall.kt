/**
 * @file Define la llamada a la API para obtener la lista de tipos de evaluación y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Interfaz para la llamada a la API de obtención de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListWorkTypeApiCall{
    /**
     * Realiza la petición a la API para obtener la lista de tipos de evaluación.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGetListWorkType].
     */
    @POST(Environment.END_POINT_GET_WORK_TYPE)
    suspend fun callApi(
        @Query ("teacher_id") teacherId : Int
    ): Response<ResponseGeneric<List<ResponseGetListWorkType?>?>>
}

/**
 * Modelo de datos para la respuesta de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListWorkType(
    @SerializedName("id")
    val workTypeId : Int?,
    @SerializedName("name")
    val name:String?,
    @SerializedName("teacher_id")
    val teacherId : Int?,
    @SerializedName("created_at")
    val createAt : String
)
