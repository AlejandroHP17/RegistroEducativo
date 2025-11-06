/**
 * @file Define la llamada a la API para el registro de escuelas y los modelos de datos asociados.
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
 * Interfaz para la llamada a la API de registro de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterCycleSchoolApiCall {
    /**
     * Realiza la petición a la API para registrar una escuela.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @POST(Environment.END_POINT_REGISTER_CYCLE_SCHOOL)
    suspend fun callApi(
        @Body request: RequestRegisterCycleSchool
    ): Response<ResponseGeneric<ResponseRegisterCycleSchool>>
}

/**
 * Modelo de datos para la petición de registro de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterCycleSchool(
    @SerializedName("teacher_id")
    val teacherId: Int?,
    @SerializedName("school_id")
    val schoolId: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("cycle_label")
    val cycleLabel: String?,
    @SerializedName("grado")
    val grade: String?,
    @SerializedName("group_name")
    val nameGroup: String?,
    @SerializedName("period_catalog_id")
    val periodCatalogId: Int?,
    @SerializedName("is_active")
    val isActive: Boolean?,
)

/**
 * Modelo de datos para la petición de registro de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseRegisterCycleSchool(
    @SerializedName("teacher_id")
    val teacherId: Int?,
    @SerializedName("school_id")
    val schoolId: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("cycle_label")
    val cycleLabel: String?,
    @SerializedName("grado")
    val grade: String?,
    @SerializedName("group_name")
    val nameGroup: String?,
    @SerializedName("period_catalog_id")
    val periodCatalogId: Int?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("id")
    val idCycleSchool: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
)