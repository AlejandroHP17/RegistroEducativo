/**
 * @file Define la llamada a la API para obtener la lista de parciales y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

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
    @GET(Environment.END_POINT_GET_PARTIAL)
    suspend fun callApi(
        @Query("school_cycle_id") schoolCycleId: Int,
    ): Response<ResponseGeneric<List<ResponsePartials?>>>
}