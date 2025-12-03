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

// Data classes for requests and responses
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