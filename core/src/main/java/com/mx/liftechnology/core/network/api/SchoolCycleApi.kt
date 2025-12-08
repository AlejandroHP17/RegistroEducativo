package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz agrupada para todas las operaciones relacionadas con ciclos escolares.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface SchoolCycleApi {

    /**
     * Obtiene la lista de grupos de un profesor.
     */
    @GET(Environment.END_POINT_GET_CYCLE_SCHOOL)
    suspend fun getGroup(@Query("teacher_id") teacherId: Int): Response<ResponseGeneric<List<ResponseGroupTeacher>>>

    /**
     * Registra un ciclo escolar.
     */
    @POST(Environment.END_POINT_REGISTER_SCHOOL_CYCLE)
    suspend fun registerSchoolCycle(@Body request: RequestRegisterSchoolCycle): Response<ResponseGeneric<ResponseRegisterSchoolCycle>>
}

/**
 * Data class que representa la petición para registrar un ciclo escolar.
 *
 * @property teacherId El ID del profesor que registra el ciclo escolar.
 * @property schoolId El ID de la escuela asociada al ciclo escolar.
 * @property name El nombre del ciclo escolar.
 * @property cycleLabel La etiqueta del ciclo (ej: "2024-2025").
 * @property grade El grado escolar.
 * @property nameGroup El nombre del grupo.
 * @property periodCatalogId El ID del catálogo de periodos.
 * @property isActive Indica si el ciclo escolar está activo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterSchoolCycle(
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("school_id")
    val schoolId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("cycle_label")
    val cycleLabel: String?,
    @SerializedName("grade")
    val grade: String?,
    @SerializedName("group_name")
    val nameGroup: String?,
    @SerializedName("period_catalog_id")
    val periodCatalogId: Int?,
    @SerializedName("is_active")
    val isActive: Boolean?,
)

/**
 * Data class que representa la respuesta del servidor después de registrar un ciclo escolar.
 *
 * @property teacherId El ID del profesor que registró el ciclo escolar.
 * @property schoolId El ID de la escuela asociada al ciclo escolar.
 * @property name El nombre del ciclo escolar.
 * @property cycleLabel La etiqueta del ciclo.
 * @property grade El grado escolar.
 * @property nameGroup El nombre del grupo.
 * @property periodCatalogId El ID del catálogo de periodos.
 * @property isActive Indica si el ciclo escolar está activo.
 * @property schoolCycleId El ID único del ciclo escolar generado por el servidor.
 * @property createdAt La fecha y hora de creación del registro.
 * @property teacherName El nombre del profesor.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseRegisterSchoolCycle(
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("school_id")
    val schoolId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("cycle_label")
    val cycleLabel: String?,
    @SerializedName("grade")
    val grade: String?,
    @SerializedName("group_name")
    val nameGroup: String?,
    @SerializedName("period_catalog_id")
    val periodCatalogId: Int?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("id")
    val schoolCycleId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("teacher_name")
    val teacherName: String,
)


/**
 * Data class que representa un grupo de un profesor obtenido del servidor.
 *
 * @property teacherId El ID del profesor.
 * @property schoolId El ID de la escuela.
 * @property name El nombre del ciclo escolar.
 * @property description La descripción del ciclo escolar.
 * @property year El año del ciclo escolar.
 * @property cycleLabel La etiqueta del ciclo.
 * @property grade El grado escolar.
 * @property groupName El nombre del grupo.
 * @property periodCatalogId El ID del catálogo de periodos.
 * @property isActive Indica si el ciclo escolar está activo.
 * @property schoolCycleId El ID único del ciclo escolar.
 * @property createdAt La fecha y hora de creación del registro.
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
    val cycleLabel: String?,
    @SerializedName("grade")
    val grade: String?,
    @SerializedName("group_name")
    val groupName: String?,
    @SerializedName("period_catalog_id")
    val periodCatalogId: Int?,
    @SerializedName("isActive")
    val isActive: Boolean?,
    @SerializedName("id")
    val schoolCycleId: Int,
    @SerializedName("created_at")
    val createdAt: String
)