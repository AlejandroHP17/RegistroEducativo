package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

fun interface GetCctApiCall {
    /** Realiza la petición al API */
    @GET(Environment.END_POINT_GET_CCT)
    suspend fun callApi(
        @Path("cct") cct: String
    ): Response<ResponseGeneric<ResponseCctSchool?>>
}

data class ResponseCctSchool(
    @SerializedName("cct")
    val cct: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombreescuela")
    val schoolName: String,
    @SerializedName("tipocicloescolar")
    val schoolCycleType: String,
    @SerializedName("tipocicloescolar_id")
    val schoolCycleTypeId: Int,
    @SerializedName("tipoescuela")
    val schoolType: String,
    @SerializedName("turno")
    val shift: String
)