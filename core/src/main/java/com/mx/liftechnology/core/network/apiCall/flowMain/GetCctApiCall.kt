package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface for the CCT API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetCctApiCall {
    /**
     * Makes the API request to get school information by CCT.
     *
     * @param cct The CCT of the school.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with [ResponseCctSchool] data.
     */
    @GET(Environment.END_POINT_GET_CCT)
    suspend fun callApi(
        @Path("cct") cct: String
    ): Response<ResponseGeneric<ResponseCctSchool?>>
}

/**
 * Data model for the CCT response.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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