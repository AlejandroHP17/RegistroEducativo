package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz agrupada para todas las operaciones relacionadas con estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface StudentApi {
    /**
     * Obtiene la lista de estudiantes.
     */
    @GET(Environment.END_POINT_GET_STUDENT)
    suspend fun getListStudents(@Query("school_cycle_id") cycleSchoolId: Int): Response<ResponseGeneric<List<ResponseGetStudent>>>

    /**
     * Registra un nuevo estudiante.
     */
    @POST(Environment.END_POINT_REGISTER_STUDENT)
    suspend fun registerStudent(@Body request: RequestRegisterStudent): Response<ResponseGeneric<ResponseRegisterStudent>>

    /**
     * Edita un estudiante existente.
     */
    @PUT(Environment.END_POINT_EDIT_STUDENT)
    suspend fun editStudent(
        @Path("student_id") studentId: Int,
        @Body request: RequestEditStudent
    ): Response<ResponseGeneric<ResponseEditStudent>>

    /**
     * Elimina un estudiante.
     */
    @DELETE(Environment.END_POINT_DELETE_STUDENT)
    suspend fun deleteStudent(@Path("student_id") studentId: Int): Response<ResponseGeneric<String>>

}

/**
 * Data class que representa la petición para registrar un nuevo estudiante.
 *
 * @property name El nombre del estudiante.
 * @property lastName El apellido paterno del estudiante.
 * @property secondLastName El apellido materno del estudiante.
 * @property curp La CURP del estudiante.
 * @property birthday La fecha de nacimiento del estudiante en formato de cadena.
 * @property phoneNumber El número de teléfono del estudiante.
 * @property schoolCycleId El ID del ciclo escolar al que pertenece el estudiante.
 * @property isActive Indica si el estudiante está activo.
 * @property teacherId El ID del profesor asociado al estudiante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterStudent(
    @SerializedName("first_name")
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("second_last_name")
    val secondLastName: String,
    @SerializedName("curp")
    val curp: String,
    @SerializedName("birth_date")
    val birthday: String?,
    @SerializedName("phone")
    val phoneNumber: String?,
    @SerializedName("school_cycle_id")
    val schoolCycleId: Int,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("teacher_id")
    val teacherId: Int
)

/**
 * Data class que representa la respuesta del servidor después de registrar un estudiante.
 *
 * @property curp La CURP del estudiante registrado.
 * @property name El nombre del estudiante.
 * @property lastName El apellido paterno del estudiante.
 * @property secondLastName El apellido materno del estudiante.
 * @property birthday La fecha de nacimiento del estudiante.
 * @property phoneNumber El número de teléfono del estudiante.
 * @property schoolCycleId El ID del ciclo escolar al que pertenece el estudiante.
 * @property isActive Indica si el estudiante está activo.
 * @property teacherId El ID del profesor asociado al estudiante.
 * @property studentId El ID único del estudiante generado por el servidor.
 * @property createdAt La fecha y hora de creación del registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseRegisterStudent(
    @SerializedName("curp")
    val curp: String,
    @SerializedName("first_name")
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("second_last_name")
    val secondLastName: String,
    @SerializedName("birth_date")
    val birthday: String?,
    @SerializedName("phone")
    val phoneNumber: String?,
    @SerializedName("school_cycle_id")
    val schoolCycleId: Int,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("id")
    val studentId: Int,
    @SerializedName("created_at")
    val createdAt: String
)

/**
 * Data class que representa la petición para editar un estudiante existente.
 * Todos los campos son opcionales para permitir actualizaciones parciales.
 *
 * @property name El nombre del estudiante (opcional).
 * @property lastName El apellido paterno del estudiante (opcional).
 * @property secondLastName El apellido materno del estudiante (opcional).
 * @property curp La CURP del estudiante (opcional).
 * @property birthday La fecha de nacimiento del estudiante (opcional).
 * @property phoneNumber El número de teléfono del estudiante (opcional).
 * @property cycleSchoolId El ID del ciclo escolar (opcional).
 * @property isActive Indica si el estudiante está activo (opcional).
 * @property teacherId El ID del profesor asociado (opcional).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestEditStudent(
    @SerializedName("first_name")
    val name: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("second_last_name")
    val secondLastName: String?,
    @SerializedName("curp")
    val curp: String?,
    @SerializedName("birth_date")
    val birthday: String?,
    @SerializedName("phone")
    val phoneNumber: String?,
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("teacher_id")
    val teacherId: Int?,
)

/**
 * Data class que representa la respuesta del servidor después de editar un estudiante.
 *
 * @property curp La CURP del estudiante.
 * @property name El nombre del estudiante.
 * @property lastName El apellido paterno del estudiante.
 * @property secondLastName El apellido materno del estudiante.
 * @property birthday La fecha de nacimiento del estudiante.
 * @property phoneNumber El número de teléfono del estudiante.
 * @property schoolCycleId El ID del ciclo escolar al que pertenece el estudiante.
 * @property isActive Indica si el estudiante está activo.
 * @property teacherId El ID del profesor asociado al estudiante.
 * @property studentId El ID único del estudiante.
 * @property createdAt La fecha y hora de creación del registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseEditStudent(
    @SerializedName("curp")
    val curp: String,
    @SerializedName("first_name")
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("second_last_name")
    val secondLastName: String,
    @SerializedName("birth_date")
    val birthday: String?,
    @SerializedName("phone")
    val phoneNumber: String?,
    @SerializedName("school_cycle_id")
    val schoolCycleId: Int,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("id")
    val studentId: Int,
    @SerializedName("created_at")
    val createdAt: String
)

/**
 * Data class que representa la información de un estudiante obtenida del servidor.
 *
 * @property curp La CURP del estudiante.
 * @property name El nombre del estudiante.
 * @property lastName El apellido paterno del estudiante.
 * @property secondLastName El apellido materno del estudiante.
 * @property birthday La fecha de nacimiento del estudiante.
 * @property phoneNumber El número de teléfono del estudiante.
 * @property userId El ID del usuario asociado al estudiante.
 * @property schoolCycleId El ID del ciclo escolar al que pertenece el estudiante.
 * @property isActive Indica si el estudiante está activo.
 * @property studentId El ID único del estudiante.
 * @property createdAt La fecha y hora de creación del registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetStudent(
    @SerializedName("curp")
    val curp: String,
    @SerializedName("first_name")
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("second_last_name")
    val secondLastName: String,
    @SerializedName("birth_date")
    val birthday: String?,
    @SerializedName("phone")
    val phoneNumber: String?,
    @SerializedName("teacher_id")
    val userId: Int,
    @SerializedName("school_cycle_d")
    val schoolCycleId: Int,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("id")
    val studentId: Int,
    @SerializedName("created_at")
    val createdAt: String
)