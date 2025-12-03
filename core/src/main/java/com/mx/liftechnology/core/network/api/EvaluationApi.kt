package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Interfaz agrupada para todas las operaciones relacionadas con evaluaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface EvaluationApi {
    /**
     * Registra evaluaciones de tipo de trabajo.
     */
    @POST(Environment.END_POINT_REGISTER_STUDENT_WORK_BULK)
    suspend fun registerWorkTypeEvaluations(@Body request: RequestWorkTypeEvaluations): Response<ResponseGeneric<ResponseWorkTypeEvaluations>>

    /**
     * Obtiene la lista de tipos de trabajo por estudiante.
     */
    @GET(Environment.END_POINT_GET_WORK_TYPE_STUDENT)
    suspend fun getListWorkTypeStudent(@Query("formative_field_id") formativeFieldId: Int): Response<ResponseGeneric<ResponseGetListWorkStudents>>


    /**
     * Obtiene la lista de evaluaciones de un estudiante.
     */
    @GET(Environment.END_POINT_GET_WORKS_STUDENT)
    suspend fun getListEvaluations(
        @Query("formative_field_id") formativeFieldId: Int,
        @Query("partial_id") partialId: Int,
        @Query("work_type_id") workTypeId: Int,
        @Query("school_cycle_id") schoolCycleId: Int,
        @Query("student_id") studentId: Int,
        @Query("work_date") workDate: String?,
        @Query("work_date_from") workDateFrom: String?,
        @Query("work_date_to") workDateTo: String?,
    ): Response<ResponseGeneric<List<ResponseGetListEvaluationsStudent>>>

    /**
     * Obtiene la lista de estudiantes por tipo de campo.
     */
    @GET(Environment.END_POINT_GET_FIELD_TYPE_STUDENTS)
    suspend fun getListByFieldTypeStudent(
        @Query("formative_field_id") formativeFieldId: Int,
        @Query("work_type_id") workTypeId: Int,
        @Query("work_name") workName: String?,
        @Query("work_date") workDate: String?
    ): Response<ResponseGeneric<ResponseGetByFieldTypeStudent>>

}

// Data classes for requests and responses
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
    val grades: List<RequestListGrades>,
)

data class RequestListGrades(
    @SerializedName("student_id")
    val studentId: Int,
    @SerializedName("grade")
    val grade: Double?,
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
    val nameWork: String,
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
    val partialName: String?,
    @SerializedName("work_type_name")
    val workTypeName: String?,
)

data class ResponseGetListWorkStudents(
    @SerializedName("formative_field_id")
    val formativeFieldId: Int,
    @SerializedName("name_formative_field")
    val nameFormativeField: String,
    @SerializedName("list_of_works")
    val listWorks: List<ResponseListWork>,
)

data class ResponseListWork(
    @SerializedName("id")
    val workId: Int,
    @SerializedName("name")
    val workName: String,
    @SerializedName("list_of_work_student")
    val listWorks: List<ResponseListWorkStudent>,
)

data class ResponseListWorkStudent(
    @SerializedName("id")
    val workStudentId: Int,
    @SerializedName("name")
    val workStudentName: String,
    @SerializedName("work_date")
    val workStudentDate: String?,
)

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
