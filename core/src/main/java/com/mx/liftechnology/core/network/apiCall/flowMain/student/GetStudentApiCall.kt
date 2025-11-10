/**
 * @file Define la llamada a la API para obtener la lista de estudiantes y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain.student

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz para la llamada a la API de obtención de la lista de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListStudentApiCall {
    /**
     * Realiza la petición a la API para obtener la lista de estudiantes.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGetStudent].
     */
    @GET(Environment.END_POINT_GET_STUDENT)
    suspend fun callApi(
        @Query("school_cycle_id") cycleSchoolId: Int
    ): Response<ResponseGeneric<List<ResponseGetStudent?>?>>
}

/**
 * Modelo de datos para la respuesta de la lista de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetStudent(
    @SerializedName("curp")
    val curp: String?,
    @SerializedName("first_name")
    val name: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("second_last_name")
    val secondLastName: String?,
    @SerializedName("birth_date")
    val birthday: String?,
    @SerializedName("phone")
    val phoneNumber: String?,
    @SerializedName("teacher_id")
    val userId: Int?,
    @SerializedName("school_cycle_d")
    val cycleSchoolId: Int,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("created_at")
    val createdAt: String
)