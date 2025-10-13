package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the school registration API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterSchoolApiCall {
    /**
     * Makes the API request to register a school.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of strings.
     */
    @POST(Environment.END_POINT_REGISTER_SCHOOL)
    suspend fun callApi(
        @Body request: RequestRegisterSchool
    ): Response<ResponseGeneric<List<String?>?>>
}

/**
 * Data model for the school registration request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterSchool(
    @SerializedName("cct")
    val cct: String?,
    @SerializedName("tipocicloescolar_id")
    val typeCycleSchoolId: Int?,
    @SerializedName("grado")
    val grade: Int?,
    @SerializedName("nombregrupo")
    val nameGroup: String?,
    @SerializedName("anio")
    val year: String?,
    @SerializedName("periodo")
    val period: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?
)