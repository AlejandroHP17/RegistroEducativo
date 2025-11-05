/**
 * @file Define la llamada a la API para obtener la lista de grupos de un profesor y los modelos de datos asociados.
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
 * Interfaz para la llamada a la API de obtención de grupos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GroupApiCall {
    /**
     * Realiza la petición a la API para obtener la lista de grupos de un profesor.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGroupTeacher].
     */
    @POST(Environment.END_POINT_GET_GROUP)
    suspend fun callApi(
        @Body request: RequestGroup
    ): Response<ResponseGeneric<List<ResponseGroupTeacher?>?>>
}

/**
 * Modelo de datos para la petición de obtención de grupos.
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
 * Modelo de datos para la respuesta de la obtención de grupos.
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
