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
    suspend fun getGroup(@Query("teacher_id") teacherId: Int): Response<ResponseGeneric<List<ResponseGroupTeacher>?>>

    /**
     * Registra un ciclo escolar.
     */
    @POST(Environment.END_POINT_REGISTER_SCHOOL_CYCLE)
    suspend fun registerSchoolCycle(@Body request: RequestRegisterSchoolCycle): Response<ResponseGeneric<ResponseRegisterSchoolCycle>>
}

/**
 * Sección para registro escolar
 * */
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
 * Sección para obtener los grupos del profesor
 * */
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