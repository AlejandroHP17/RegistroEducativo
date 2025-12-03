package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SchoolApi {
    /**
     * Obtiene información de una escuela por CCT.
     */
    @GET(Environment.END_POINT_GET_CCT)
    suspend fun getCct(@Path("cct") cct: String): Response<ResponseGeneric<ResponseCctSchool>>
}

/**
 * Sección para obtener la informacion de la escuela por CCT
 * */
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