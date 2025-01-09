package com.mx.liftechnology.core.network.callapi

import com.mx.liftechnology.core.model.modelApi.CctSchool
import com.mx.liftechnology.core.model.modelApi.GenericResponse
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface SchoolCCTApiCall {
    /** Realiza la petición al API */
    @GET(Environment.END_POINT_GET_CCT)
    suspend fun callApi(
        @Path("cct") cct: String): Response<GenericResponse<CctSchool?>>
}
