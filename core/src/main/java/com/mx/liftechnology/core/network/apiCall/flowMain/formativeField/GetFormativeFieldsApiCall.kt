/**
 * @file Define la llamada a la API para obtener la lista de materias y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain.formativeField

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz para la llamada a la API de obtención de la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListFormativeFieldsApiCall {
    /**
     * Realiza la petición a la API para obtener la lista de materias.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGetListFormativeFields].
     */
    @GET(Environment.END_POINT_GET_FORMATIVE_FIELDS)
    suspend fun callApi(
        @Query("school_cycle_id") cycleSchoolId: Int
    ): Response<ResponseGeneric<List<ResponseGetListFormativeFields?>?>>
}

/**
 * Modelo de datos para la respuesta de la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListFormativeFields(
    @SerializedName("school_cycle_d")
    val cycleSchoolId: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("id")
    val formativeFieldID: Int?,
    @SerializedName("created_at")
    val createdAt: String
)