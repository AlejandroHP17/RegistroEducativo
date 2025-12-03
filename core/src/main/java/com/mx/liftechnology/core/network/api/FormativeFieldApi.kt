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
     * Obtiene la lista de campos formativos con tipos de trabajo.
     */
    @GET(Environment.END_POINT_GET_FORMATIVE_FIELD_WORK_TYPE)
    suspend fun getListWotyFofi(@Path("school_cycle_id") schoolCycleId: Int): Response<ResponseGeneric<ResponseGetListWotyFofi>>


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

/**
 * Data class que representa la petición para registrar un campo formativo con sus tipos de trabajo y evaluaciones.
 *
 * @property cycleSchoolId El ID del ciclo escolar.
 * @property formativeFieldName El nombre del campo formativo.
 * @property code El código del campo formativo.
 * @property workTypes La lista de tipos de trabajo asociados al campo formativo.
 * @property evaluations La lista de evaluaciones asociadas al campo formativo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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

/**
 * Data class que representa un tipo de trabajo en la petición de registro de campo formativo.
 *
 * @property workTypeId El ID del tipo de trabajo (opcional, para tipos existentes).
 * @property workTypeName El nombre del tipo de trabajo (opcional, para nuevos tipos).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestWorkType(
    @SerializedName("id")
    val workTypeId: Int?,
    @SerializedName("name")
    val workTypeName: String?,
)

/**
 * Data class que representa una evaluación en la petición de registro de campo formativo.
 *
 * @property partialId El ID del parcial.
 * @property workTypeId El ID del tipo de trabajo (opcional).
 * @property workTypeName El nombre del tipo de trabajo.
 * @property evaluationWeight El peso de la evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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

/**
 * Data class que representa la respuesta del servidor después de registrar un campo formativo.
 *
 * @property cycleSchoolId El ID del ciclo escolar.
 * @property formativeFieldsName El nombre del campo formativo registrado.
 * @property formativeFieldsCode El código del campo formativo.
 * @property formativeFieldsId El ID único del campo formativo generado por el servidor.
 * @property createdAt La fecha y hora de creación del registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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

/**
 * Data class que representa un campo formativo en la lista obtenida del servidor.
 *
 * @property schoolCycleId El ID del ciclo escolar al que pertenece el campo formativo.
 * @property name El nombre del campo formativo.
 * @property code El código del campo formativo.
 * @property formativeFieldId El ID único del campo formativo.
 * @property createdAt La fecha y hora de creación del registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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



/**
 * Data class que representa la respuesta con campos formativos y sus tipos de trabajo.
 *
 * @property schoolCycleId El ID del ciclo escolar.
 * @property formativeFields La lista de campos formativos con sus tipos de trabajo asociados.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListWotyFofi(
    @SerializedName("school_cycle_id")
    val schoolCycleId: Int,
    @SerializedName("formative_fields")
    val formativeFields: List<ResponseFormativeFields>,
)

/**
 * Data class que representa un campo formativo con sus tipos de trabajo.
 *
 * @property formativeFieldId El ID único del campo formativo.
 * @property formativeFieldName El nombre del campo formativo.
 * @property code El código del campo formativo.
 * @property listWorkTypes La lista de tipos de trabajo asociados al campo formativo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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

/**
 * Data class que representa un tipo de trabajo asociado a un campo formativo.
 *
 * @property workTypeId El ID único del tipo de trabajo.
 * @property workTypeName El nombre del tipo de trabajo.
 * @property evaluationWeight El peso de evaluación del tipo de trabajo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseWorkTypes(
    @SerializedName("work_type_id")
    val workTypeId: Int,
    @SerializedName("work_type_name")
    val workTypeName: String,
    @SerializedName("evaluation_weight")
    val evaluationWeight: String
)

/**
 * Data class que representa la respuesta con trabajos agrupados por tipo de campo y estudiante.
 *
 * @property formativeFieldId El ID del campo formativo.
 * @property formativeFieldName El nombre del campo formativo.
 * @property workTypeId El ID del tipo de trabajo.
 * @property workTypeName El nombre del tipo de trabajo.
 * @property works La lista de trabajos con sus estudiantes asociados.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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

/**
 * Data class que representa un trabajo con sus estudiantes asociados.
 *
 * @property workId El ID único del trabajo.
 * @property workName El nombre del trabajo.
 * @property workDate La fecha del trabajo.
 * @property listStudents La lista de estudiantes asociados al trabajo con sus calificaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
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

/**
 * Data class que representa un estudiante asociado a un trabajo con su calificación.
 *
 * @property studentId El ID del estudiante.
 * @property studentName El nombre del estudiante.
 * @property grade La calificación del estudiante (puede ser nula si no se ha calificado).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListByFieldStudent(
    @SerializedName("student_id")
    val studentId: Int,
    @SerializedName("student_name")
    val studentName: String,
    @SerializedName("grade")
    val grade: String?,
)

