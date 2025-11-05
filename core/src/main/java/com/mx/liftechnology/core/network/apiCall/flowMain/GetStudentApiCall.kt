/**
 * @file Define la llamada a la API para obtener la lista de estudiantes y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

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
    @POST(Environment.END_POINT_GET_STUDENT)
    suspend fun callApi(
        @Body request: RequestGetListStudent
    ): Response<ResponseGeneric<List<ResponseGetStudent?>?>>
}

/**
 * Modelo de datos para la petición de la lista de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetListStudent(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?
)

/**
 * Modelo de datos para la respuesta de la lista de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetStudent(
    @SerializedName("id")
    val id: String?,
    @SerializedName("alumno_id")
    val studentId: String?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: String?,
    @SerializedName("curp")
    val curp: String?,
    @SerializedName("fechanacimiento")
    val birthday: String?,
    @SerializedName("celular")
    val phoneNumber: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("paterno")
    val lastName: String?,
    @SerializedName("materno")
    val secondLastName: String?
)