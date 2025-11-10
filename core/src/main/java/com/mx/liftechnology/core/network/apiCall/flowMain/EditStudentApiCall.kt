package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfaz para la llamada a la API de eliminar estudiante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface EditStudentApiCall {

    @PUT(Environment.END_POINT_EDIT_STUDENT)
    suspend fun callApi(
        @Path("student_id") studentId: Int,
        @Body request: RequestEditStudent
    ): Response<ResponseGeneric<ResponseEditStudent>>
}

data class RequestEditStudent(
    @SerializedName("first_name")
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("second_last_name")
    val secondLastName: String,
    @SerializedName("curp")
    val curp: String,
    @SerializedName("birth_date")
    val birthday: String,
    @SerializedName("phone")
    val phoneNumber: String,
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
    val birthday: String,
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("teacher_id")
    val teacherId: Int?,
    @SerializedName("id")
    val studentId: Int?,
    @SerializedName("created_at")
    val createdAt: String?
)