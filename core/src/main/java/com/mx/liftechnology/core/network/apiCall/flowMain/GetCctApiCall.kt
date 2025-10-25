/**
 * @file Define la llamada a la API para obtener la información de una escuela a partir de su CCT y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interfaz para la llamada a la API de obtención de CCT.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetCctApiCall {
    /**
     * Realiza la petición a la API para obtener la información de una escuela a partir de su CCT.
     *
     * @param cct El CCT de la escuela.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con los datos de [ResponseCctSchool].
     */
    @GET(Environment.END_POINT_GET_CCT)
    suspend fun callApi(
        @Path("cct") cct: String
    ): Response<ResponseGeneric<ResponseCctSchool?>?>
}

/**
 * Modelo de datos para la respuesta de la obtención de CCT.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseCctSchool(
    @SerializedName("cct")
    val cct: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombreescuela")
    val schoolName: String,
    @SerializedName("tipocicloescolar")
    val schoolCycleType: String,
    @SerializedName("tipocicloescolar_id")
    val schoolCycleTypeId: Int,
    @SerializedName("tipoescuela")
    val schoolType: String,
    @SerializedName("turno")
    val shift: String
)