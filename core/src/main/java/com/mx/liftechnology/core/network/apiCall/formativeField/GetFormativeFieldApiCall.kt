package com.mx.liftechnology.core.network.apiCall.formativeField

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
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGetListFormativeField].
     */
    @GET(Environment.END_POINT_GET_FORMATIVE_FIELDS)
    suspend fun callApi(
        @Query("school_cycle_id") cycleSchoolId: Int
    ): Response<ResponseGeneric<List<ResponseGetListFormativeField>?>>
}

/**
 * Modelo de datos para la respuesta de la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListFormativeField(
    @SerializedName("school_cycle_d")
    val schoolCycleId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String?,
    @SerializedName("id")
    val formativeFieldId: Int,
    @SerializedName("created_at")
    val createdAt: String
)