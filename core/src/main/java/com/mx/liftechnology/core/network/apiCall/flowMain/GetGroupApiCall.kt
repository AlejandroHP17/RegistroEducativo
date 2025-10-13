package com.mx.liftechnology.core.network.apiCall.flowMain

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface for the group API call.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GroupApiCall {
    /**
     * Makes the API request to get the list of groups for a teacher.
     *
     * @param request The request data.
     * @return A Retrofit [Response] containing a [ResponseGeneric] with a list of [ResponseGroupTeacher] data.
     */
    @POST(Environment.END_POINT_GET_GROUP)
    suspend fun callApi(
        @Body request: RequestGroup
    ): Response<ResponseGeneric<List<ResponseGroupTeacher?>?>>
}

/**
 * Data model for the group request.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGroup(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?
)

/**
 * Data model for the group response.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGroupTeacher(
    @SerializedName("cct")
    val cct: String,
    @SerializedName("cicloescolar_id")
    val schoolYearId: Int,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("escuelaciclo_id")
    val cycleSchoolId: Int,
    @SerializedName("grupo")
    val group: String,
    @SerializedName("nombre")
    val name: String,
    @SerializedName("nombreescuela")
    val nameSchool: String,
    @SerializedName("profesor_id")
    val teacherId: Int,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int,
    @SerializedName("tipo")
    val type: String,
    @SerializedName("turno")
    val shift: String
)
