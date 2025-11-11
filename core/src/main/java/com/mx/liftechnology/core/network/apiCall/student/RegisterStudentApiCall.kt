package com.mx.liftechnology.core.network.apiCall.student

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz para la llamada a la API de registro de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterStudentApiCall {
    /**
     * Realiza la petición a la API para registrar un estudiante.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @POST(Environment.END_POINT_REGISTER_STUDENT)
    suspend fun callApi(
        @Body request: RequestRegisterStudent
    ): Response<ResponseGeneric<ResponseRegisterStudent>>
}

/**
 * Modelo de datos para la petición de registro de estudiantes.
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
 * Modelo de datos para la petición de registro de estudiantes.
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