/**
 * @file Define la llamada a la API para el registro de estudiantes y los modelos de datos asociados.
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
    ): Response<ResponseGeneric<List<String?>?>>
}

/**
 * Modelo de datos para la petición de registro de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterStudent(
    @SerializedName("nombres")
    val name: String,
    @SerializedName("paterno")
    val lastName: String,
    @SerializedName("materno")
    val secondLastName: String,
    @SerializedName("curp")
    val curp: String,
    @SerializedName("fechanacimiento")
    val birthday: String,
    @SerializedName("celular")
    val phoneNumber: String,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
)