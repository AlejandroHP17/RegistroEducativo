package com.mx.liftechnology.core.network.apiCall.student

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetListEvaluationsStudentApiCall {
    @GET(Environment.END_POINT_GET_WORKS_STUDENT)
    suspend fun callApi(
        @Query("formative_field_id")formativeFieldId: Int,
        @Query("partial_id")partialId: Int,
        @Query("work_type_id")workTypeId: Int,
        @Query("school_cycle_id")schoolCycleId: Int,
        @Query("student_id")studentId: Int,
        @Query("work_date")workDate: String?,
        @Query("work_date_from")workDateFrom: String?,
        @Query("work_date_to")workDateTo: String?,
    ): Response<ResponseGeneric<List<ResponseGetListEvaluationsStudent>>>
}

data class ResponseGetListEvaluationsStudent(
    @SerializedName("id")
    val evaluationId: Int,
    @SerializedName("student_id")
    val studentId: Int,
    @SerializedName("formative_field_id")
    val formativeFieldId: Int,
    @SerializedName("work_type_id")
    val workTypeId: Int,
    @SerializedName("name")
    val evaluationName: String,
    @SerializedName("grade")
    val grade: Double?,
    @SerializedName("work_date")
    val workDate: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("student_name")
    val studentName: String,
    @SerializedName("formative_field_name")
    val formativeFieldName: String,
    @SerializedName("work_type_name")
    val workTypeName: String,
)