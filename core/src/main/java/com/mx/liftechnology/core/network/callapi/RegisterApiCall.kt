package com.mx.liftechnology.core.network.callapi

import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.network.enviroment.Environment
import com.mx.liftechnology.core.util.GenericResponse
import retrofit2.Response
import retrofit2.http.GET


interface RegisterApiCall {
    /** Realiza la petición al API */
    @GET(Environment.END_POINT_CCT)
    suspend fun callApi(): Response<GenericResponse<DataCCT>?>
}
