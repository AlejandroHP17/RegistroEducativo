package com.mx.liftechnology.core.network.apiCall.schoolCycle

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz para la llamada a la API de obtención de grupos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GroupApiCall {
    /**
     * Realiza la petición a la API para obtener la lista de grupos de un profesor.
     *
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGroupTeacher].
     */
    @GET(Environment.END_POINT_GET_CYCLE_SCHOOL)
    suspend fun callApi(
        @Query("teacher_id") teacherId: Int
    ): Response<ResponseGeneric<List<ResponseGroupTeacher>?>>
}

/**
 * Modelo de datos para la respuesta de la obtención de grupos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGroupTeacher(
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("school_id")
    val schoolId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("year")
    val year: Int,
    @SerializedName("cycle_label")
    val cycleLabel : String?,
    @SerializedName("grade")
    val grade: String?,
    @SerializedName("group_name")
    val groupName: String?,
    @SerializedName("period_catalog_id")
    val periodCatalogId : Int?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("id")
    val schoolCycleId : Int,
    @SerializedName("created_at")
    val createdAt: String
)
