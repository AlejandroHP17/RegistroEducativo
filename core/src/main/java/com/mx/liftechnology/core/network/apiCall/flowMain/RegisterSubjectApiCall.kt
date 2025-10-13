package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the subject registration API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterSubjectApiCall {
    /**
     * Makes the API request to register a subject.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of strings.
     */
    @POST(Environment.END_POINT_REGISTER_SUBJECT)
    suspend fun callApi(
        @Body request: RequestRegisterSubject
    ): Response<ResponseGeneric<List<String?>?>>
}

/**
 * Data model for the subject registration request.
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
 * Data model for the percentage in the subject registration request.
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
