package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PartialApi {
    /**
     * Obtiene la lista de parciales.
     */
    @GET(Environment.END_POINT_GET_PARTIAL)
    suspend fun getListPartial(@Query("school_cycle_id") schoolCycleId: Int): Response<ResponseGeneric<List<ResponseGetPartials>>>


    /**
     * Registra una lista de parciales.
     */
    @POST(Environment.END_POINT_REGISTER_PARTIAL)
    suspend fun registerListPartial(@Body request: RequestRegisterPartial): Response<ResponseGeneric<List<ResponseRegisterPartial>>>

}

/**
 * Sección para obtener los parciales del ciclo escolar activo
 * */
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

/**
 * Sección para el registro de parciales al ciclo escolar
 * */
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

