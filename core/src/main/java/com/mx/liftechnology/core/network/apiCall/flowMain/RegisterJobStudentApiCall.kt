package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the student job registration API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterJobStudentApiCall {
    /**
     * Makes the API request to register a student job.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of [ResponseStudentJobs] data.
     */
    @POST(Environment.END_POINT_REGISTER_JOB)
    suspend fun callApi(
        @Body request: RequestRegisterJobStudent,
    ): Response<ResponseGeneric<List<ResponseStudentJobs?>?>>
}

/**
 * Data model for the student job registration request.
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
 * Data model for the student jobs in the registration request.
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
 * Data model for the student jobs response.
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
