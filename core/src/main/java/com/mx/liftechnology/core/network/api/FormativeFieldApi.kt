package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz agrupada para todas las operaciones relacionadas con campos formativos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface FormativeFieldApi {
    /**
     * Obtiene la lista de campos formativos.
     */
    @GET(Environment.END_POINT_GET_FORMATIVE_FIELDS)
    suspend fun getListFormativeFields(@Query("school_cycle_id") cycleSchoolId: Int): Response<ResponseGeneric<List<ResponseGetListFormativeField>>>

    /**
     * Obtiene la lista de tipos de trabajo.
     */
    @GET(Environment.END_POINT_GET_WORK_TYPE)
    suspend fun getListWorkType(@Query("teacher_id") teacherId: Int): Response<ResponseGeneric<List<ResponseGetListWorkType>>>

    /**
     * Obtiene el tipo de trabajo por campo formativo.
     */
    @GET(Environment.END_POINT_GET_WORK_TYPE_BY)
    suspend fun getWorkTypeByFormativeField(@Path("formative_field_id") formativeFieldId: Int): Response<ResponseGeneric<ResponseGetWorkType>>

    /**
     * Obtiene la lista de campos formativos con tipos de trabajo.
     */
    @GET(Environment.END_POINT_GET_FORMATIVE_FIELD_WORK_TYPE)
    suspend fun getListWotyFofi(@Path("school_cycle_id") schoolCycleId: Int): Response<ResponseGeneric<ResponseGetListWotyFofi>>

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

    /**
     * Registra campos formativos en bulk.
     */
    @POST(Environment.END_POINT_REGISTER_FORMATIVE_FIELDS_BULK)
    suspend fun registerFormativeFieldsBulk(@Body request: RequestRegisterFormativeField): Response<ResponseGeneric<ResponseFormativeFieldBulk>>

    /**
     * Elimina un campo formativo.
     */
    @DELETE(Environment.END_POINT_DELETE_FORMATIVE_FIELDS)
    suspend fun deleteFormativeField(@Path("field_id") fieldId: Int): Response<ResponseGeneric<String>>
}

// Data classes for requests and responses
data class RequestRegisterFormativeField(
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int,
    @SerializedName("name")
    val formativeFieldName: String,
    @SerializedName("code")
    val code: String?,
    @SerializedName("work_types")
    val workTypes: List<RequestWorkType>,
    @SerializedName("evaluations")
    val evaluations: List<RequestEvaluations>
)

data class RequestWorkType(
    @SerializedName("id")
    val workTypeId: Int?,
    @SerializedName("name")
    val workTypeName: String?,
)

data class RequestEvaluations(
    @SerializedName("partial_id")
    val partialId: Int,
    @SerializedName("work_type_id")
    val workTypeId: Int?,
    @SerializedName("work_type_name")
    val workTypeName: String,
    @SerializedName("evaluation_weight")
    val evaluationWeight: Int
)

data class ResponseFormativeFieldBulk(
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int,
    @SerializedName("name")
    val formativeFieldsName: String,
    @SerializedName("code")
    val formativeFieldsCode: String?,
    @SerializedName("id")
    val formativeFieldsId: Int,
    @SerializedName("created_at")
    val createdAt: String
)

data class ResponseGetListFormativeField(
    @SerializedName("school_cycle_d")
    val schoolCycleId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String?,
    @SerializedName("id")
    val formativeFieldId: Int,
    @SerializedName("created_at")
    val createdAt: String
)

data class ResponseGetListWorkType(
    @SerializedName("id")
    val workTypeId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("created_at")
    val createAt: String
)

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

data class ResponseGetByFieldTypeStudent(
    @SerializedName("formative_field_id")
    val formativeFieldId: Int,
    @SerializedName("formative_field_name")
    val formativeFieldName: String,
    @SerializedName("work_type_id")
    val workTypeId: Int,
    @SerializedName("work_type_name")
    val workTypeName: String,
    @SerializedName("works")
    val works: List<ResponseGetListByFieldTypeStudent>
)

data class ResponseGetListByFieldTypeStudent(
    @SerializedName("id")
    val workId: Int,
    @SerializedName("name")
    val workName: String,
    @SerializedName("work_date")
    val workDate: String?,
    @SerializedName("students")
    val listStudents: List<ResponseGetListByFieldStudent>,
)

data class ResponseGetListByFieldStudent(
    @SerializedName("student_id")
    val studentId: Int,
    @SerializedName("student_name")
    val studentName: String,
    @SerializedName("grade")
    val grade: String?,
)

