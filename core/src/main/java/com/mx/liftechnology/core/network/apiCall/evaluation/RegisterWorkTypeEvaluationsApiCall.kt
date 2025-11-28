package com.mx.liftechnology.core.network.apiCall.evaluation

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

fun interface RegisterWorkTypeEvaluationsApiCall {
    @POST(Environment.END_POINT_REGISTER_STUDENT_WORK_BULK)
    suspend fun callApi(
        @Body request: RequestWorkTypeEvaluations
    ): Response<ResponseGeneric<ResponseWorkTypeEvaluations>>
}

data class RequestWorkTypeEvaluations(
    @SerializedName("formative_field_id")
    val formativeFieldId: Int,
    @SerializedName("partial_id")
    val partialId: Int,
    @SerializedName("work_type_id")
    val workTypeId: Int,
    @SerializedName("name")
    val nameWork: String,
    @SerializedName("work_date")
    val workDate: String?,
    @SerializedName("school_cycle_id")
    val schoolCycleId: Int?,
    @SerializedName("grades")
    val grades : List<RequestListGrades>
)

data class RequestListGrades(
    @SerializedName("student_id")
    val studentId: Int,
    @SerializedName("grade")
    val grade: Double?
)

data class ResponseWorkTypeEvaluations(
    @SerializedName("created")
    val createdWorks: List<ResponseCreatedWorks>,
    @SerializedName("total_with_grade")
    val totalStudentsWithGrade: Int,
    @SerializedName("total_without_grade")
    val totalStudentsWithoutGrade: Int,
    @SerializedName("formative_field_name")
    val formativeFieldName: String?,
    @SerializedName("partial_name")
    val partialName: String?,
    @SerializedName("work_type_id")
    val workTypeId: Int,
    @SerializedName("work_type_name")
    val workTypeName: String,
    @SerializedName("name")
    val nameWork: String
)

data class ResponseCreatedWorks(
    @SerializedName("student_id")
    val studentId: Int,
    @SerializedName("formative_field_id")
    val formativeFieldId: Int,
    @SerializedName("partial_id")
    val partialId: Int,
    @SerializedName("work_type_id")
    val workTypeId: Int,
    @SerializedName("name")
    val nameWork: String,
    @SerializedName("grade")
    val grade: Double,
    @SerializedName("work_date")
    val workDate: String?,
    @SerializedName("id")
    val workId: Int,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("student_name")
    val studentName: String?,
    @SerializedName("formative_field_name")
    val formativeFieldName: String?,
    @SerializedName("partial_name")
    val partialName : String?,
    @SerializedName("work_type_name")
    val workTypeName : String?,
)