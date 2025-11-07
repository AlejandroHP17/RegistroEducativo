/**
 * @file Define la llamada a la API para el registro de una lista de parciales y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz para la llamada a la API de registro de una lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListPartialApiCall {
    /**
     * Realiza la petición a la API para registrar una lista de parciales.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @POST(Environment.END_POINT_REGISTER_PARTIAL)
    suspend fun callApi(
        @Body request: RequestRegisterPartial
    ): Response<ResponseGeneric<List<ResponsePartials?>>>
}

/**
 * Modelo de datos para la petición de registro de una lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterPartial(
    @SerializedName("partials")
    val listPartials: List<RequestPartials?>
)

/**
 * Modelo de datos para un parcial en la petición de registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestPartials(
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int?,
    @SerializedName("name")
    val description: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?
)

/**
 * Modelo de datos para un parcial en la petición de registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponsePartials(
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int?,
    @SerializedName("name")
    val description: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("id")
    val partialId : Int,
    @SerializedName("created_at")
    val createdAt : String?
)