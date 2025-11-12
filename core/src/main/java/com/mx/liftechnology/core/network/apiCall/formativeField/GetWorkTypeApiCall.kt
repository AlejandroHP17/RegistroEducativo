package com.mx.liftechnology.core.network.apiCall.formativeField

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


fun interface GetWorkTypeApiCall {
    @GET(Environment.END_POINT_GET_WORK_TYPE_BY)
    suspend fun callApi(
        @Path("formative_field_id") formativeFieldId  : Int
    ): Response<ResponseGeneric<ResponseGetWorkType>>
}

data class ResponseGetWorkType(
    @SerializedName("formative_field_name")
    val formativeFieldName: String,
    @SerializedName("formative_field_id")
    val formativeFieldId: Int,
    @SerializedName("work_types")
    val workTypes: List<ResponseWorkTypeDetail>
)

data class ResponseWorkTypeDetail(
    @SerializedName("work_type_name")
    val workTypeName: String,
    @SerializedName("work_type_id")
    val workTypeId: Int,
    @SerializedName("evaluation_weight")
    val evaluationWeight: String
)