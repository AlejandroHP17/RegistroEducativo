/**
 * @file Define la llamada a la API para el registro de trabajos de estudiantes y los modelos de datos asociados.
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
 * Interfaz para la llamada a la API de registro de trabajos de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterJobStudentApiCall {
    /**
     * Realiza la petición a la API para registrar un trabajo de estudiante.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseStudentJobs].
     */
    @POST(Environment.END_POINT_REGISTER_JOB)
    suspend fun callApi(
        @Body request: RequestRegisterJobStudent,
    ): Response<ResponseGeneric<List<ResponseStudentJobs?>?>>
}

/**
 * Modelo de datos para la petición de registro de trabajos de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterJobStudent(
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("fecha")
    val date: String,
    @SerializedName("numero")
    val number: Int,
    @SerializedName("tipotrabajopecg_id")
    val typeJobPecgId: Int,
    @SerializedName("campoformativopecgporcentaje_id")
    val fieldPecgPercentId: Int,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("parcialciclogrupo_id")
    val partialCycleGroupId: Int,
    @SerializedName("diaparcialciclogrupo_id")
    val dayPartialCycleGroupId: Int,
    @SerializedName("alumnostrabajos")
    val studentJobs: List<RequestStudentJobs>,
)

/**
 * Modelo de datos para los trabajos de los estudiantes en la petición de registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestStudentJobs(
    @SerializedName("alumnoescuelaciclogrupo_id")
    val studentSchoolCycleGroupId: Int,
    @SerializedName("calificacion")
    val qualification: Float,
    @SerializedName("comentario")
    val comment: String,
)

/**
 * Modelo de datos para la respuesta de los trabajos de los estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseStudentJobs(
    @SerializedName("fecha")
    val date: String,
    @SerializedName("alumnoescuelaciclogrupo_id")
    val studentSchoolCycleGroupId: Int,
    @SerializedName("resultado")
    val result: String,
)
