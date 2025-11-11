package com.mx.liftechnology.core.network.apiCall.formativeField

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

fun interface GetListWotyFofiApiCall{
    @GET(Environment.END_POINT_GET_FORMATIVE_FIELD_WORK_TYPE)
    suspend fun callApi(
        @Path("school_cycle_id") schoolCycleId: Int
    ): Response<ResponseGeneric<ResponseGetListWotyFofi>>
}

data class ResponseGetListWotyFofi(
    @SerializedName("school_cycle_id")
    val schoolCycleId: Int,
    @SerializedName("formative_fields")
    val formativeFields: List<ResponseFormativeFields>,
)

data class ResponseFormativeFields(
    @SerializedName("formative_field_id")
    val formativeFieldId: Int,
    @SerializedName("name")
    val formativeFieldName: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("work_types")
    val listWorkTypes: List<ResponseWorkTypes>
)

data class ResponseWorkTypes(
    @SerializedName("work_type_id")
    val workTypeId: Int,
    @SerializedName("work_type_name")
    val workTypeName: String,
    @SerializedName("evaluation_weight")
    val evaluationWeight: String
)