/**
 * @file Define la llamada a la API para el registro de materias y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz para la llamada a la API de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterSubjectApiCall {
    /**
     * Realiza la petición a la API para registrar una materia.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @POST(Environment.END_POINT_REGISTER_SUBJECT)
    suspend fun callApi(
        @Body request: RequestRegisterSubject
    ): Response<ResponseGeneric<List<String?>?>>
}

/**
 * Modelo de datos para la petición de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterSubject(
    @SerializedName("campoformativo")
    val subject: String?,
    @SerializedName("opciones")
    val options: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("porcentajes")
    val percents: List<RequestPercent?>
)

/**
 * Modelo de datos para el porcentaje en la petición de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestPercent(
    @SerializedName("trabajo_id")
    val jobId: Int?,
    @SerializedName("porcentaje")
    val percent: Int?,
    @SerializedName("trabajo_descripcion")
    val assessmentType: String?
)
