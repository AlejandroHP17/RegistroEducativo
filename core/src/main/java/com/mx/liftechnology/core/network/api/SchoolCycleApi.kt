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
     * Obtiene información de una escuela por CCT.
     */
    @GET(Environment.END_POINT_GET_CCT)
    suspend fun getCct(@Path("cct") cct: String): Response<ResponseGeneric<ResponseCctSchool>>

    /**
     * Obtiene la lista de grupos de un profesor.
     */
    @GET(Environment.END_POINT_GET_CYCLE_SCHOOL)
    suspend fun getGroup(@Query("teacher_id") teacherId: Int): Response<ResponseGeneric<List<ResponseGroupTeacher>?>>

    /**
     * Obtiene la lista de parciales.
     */
    @GET(Environment.END_POINT_GET_PARTIAL)
    suspend fun getListPartial(@Query("school_cycle_id") schoolCycleId: Int): Response<ResponseGeneric<List<ResponseGetPartials>>>

    /**
     * Registra un ciclo escolar.
     */
    @POST(Environment.END_POINT_REGISTER_SCHOOL_CYCLE)
    suspend fun registerSchoolCycle(@Body request: RequestRegisterSchoolCycle): Response<ResponseGeneric<ResponseRegisterSchoolCycle>>

    /**
     * Registra una lista de parciales.
     */
    @POST(Environment.END_POINT_REGISTER_PARTIAL)
    suspend fun registerListPartial(@Body request: RequestRegisterPartial): Response<ResponseGeneric<List<ResponseRegisterPartial>>>
}

// Data classes for requests and responses
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

data class ResponseCctSchool(
    @SerializedName("cct")
    val cct: String,
    @SerializedName("school_type_id")
    val schoolTypeId: Int,
    @SerializedName("name")
    val schoolName: String,
    @SerializedName("postal_code")
    val postalCode: String?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?,
    @SerializedName("shift_id")
    val shiftId: Int?,
    @SerializedName("shift_name")
    val shiftName: String?,
    @SerializedName("id")
    val schoolId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("period_catalog")
    val periodCatalog: List<ResponsePeriodCatalog>?
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

data class RequestRegisterPartial(
    @SerializedName("partials")
    val listPartials: List<RequestPartials?>
)

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

data class ResponseRegisterPartial(
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int?,
    @SerializedName("name")
    val description: String,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("id")
    val partialId: Int,
    @SerializedName("created_at")
    val createdAt: String?
)

data class ResponseGetPartials(
    @SerializedName("school_cycle_id")
    val schoolCycleId: Int,
    @SerializedName("name")
    val description: String,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("id")
    val partialId: Int,
    @SerializedName("created_at")
    val createdAt: String
)

