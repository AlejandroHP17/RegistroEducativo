package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the partial list registration API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListPartialApiCall {
    /**
     * Makes the API request to register a list of partials.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of strings.
     */
    @POST(Environment.END_POINT_REGISTER_PARTIAL)
    suspend fun callApi(
        @Body request: RequestRegisterPartial
    ): Response<ResponseGeneric<List<String?>?>>
}

/**
 * Data model for the partial list registration request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterPartial(
    @SerializedName("numparciales")
    val numberPartials: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("parciales")
    val listPartials: List<RequestPartials?>
)

/**
 * Data model for a partial in the registration request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestPartials(
    @SerializedName("descripcion")
    val description: String?,
    @SerializedName("fechainicio")
    val startDate: String?,
    @SerializedName("fechafinal")
    val endDate: String?
)