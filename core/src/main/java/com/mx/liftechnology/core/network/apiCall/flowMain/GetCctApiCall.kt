/**
 * @file Define la llamada a la API para obtener la información de una escuela a partir de su CCT y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
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
    @SerializedName("school_type_id")
    val schoolTypeId: Int,
    @SerializedName("name")
    val schoolName: String,
    @SerializedName("postal_code")
    val postalCode: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("shift_id")
    val shiftId: Int,
    @SerializedName("shift_name")
    val shiftName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("period_catalog")
    val periodCatalog: List<ResponsePeriodCatalog>
)

data class ResponsePeriodCatalog(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type_name")
    val typeName: String,
    @SerializedName("period_number")
    val periodNumber: Int,
    @SerializedName("created_at")
    val createdAt: String
)