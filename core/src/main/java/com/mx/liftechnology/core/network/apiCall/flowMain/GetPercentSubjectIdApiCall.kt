/**
 * @file Define la llamada a la API para obtener el porcentaje de una materia y los modelos de datos asociados.
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
 * Interfaz para la llamada a la API de obtención del porcentaje de una materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface GetPercentSubjectIdApiCall {
    /**
     * Realiza la petición a la API para obtener el porcentaje de una materia.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de [ResponseGetPercentSubjectId].
     */
    @POST(Environment.END_POINT_GET_SUBJECT_PERCENT)
    suspend fun callApi(
        @Body request: RequestGetPercentSubjectId
    ): Response<ResponseGeneric<List<ResponseGetPercentSubjectId>?>?>
}

/**
 * Modelo de datos para la petición del porcentaje de una materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestGetPercentSubjectId(
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("campoformativopecg_id")
    val subjectSchoolCycleGroupId: Int?
)

/**
 * Modelo de datos para la respuesta del porcentaje de una materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetPercentSubjectId(
    @SerializedName("id")
    val id :Int?,
    @SerializedName("porcentaje")
    val percent:Int?,
    @SerializedName("campoformativopecg_id")
    val subjectSchoolCycleGroupId :	Int?,
    @SerializedName("cf_descripcion")
    val description	:	String?,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int?,
    @SerializedName("tt_id")
    val assignmentId: Int?,
    @SerializedName("tt_descripcion")
    val assignmentName:String?
)