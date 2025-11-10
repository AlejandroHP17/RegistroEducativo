/**
 * @file Define la llamada a la API para el registro de materias y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowMain.formativeField

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz para la llamada a la API de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterFormativeFieldsBulkApiCall {
    /**
     * Realiza la petición a la API para registrar una materia.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @POST(Environment.END_POINT_REGISTER_FORMATIVE_FIELDS_BULK)
    suspend fun callApi(
        @Body request: RequestRegisterFormativeFields
    ): Response<ResponseGeneric<ResponseFormativeFieldBulk>>
}

/**
 * Modelo de datos para la petición de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterFormativeFields(
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int?,
    @SerializedName("name")
    val formativeFieldName: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("work_types")
    val workTypes: List<RequestWorkType>,
    @SerializedName("evaluations")
    val evaluations: List<RequestEvaluations?>
)

data class RequestWorkType(
    @SerializedName("id")
    val workTypeId: Int?,
    @SerializedName("name")
    val workTypeName: String?,
)

/**
 * Modelo de datos para el porcentaje en la petición de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestEvaluations(
    @SerializedName("partial_id")
    val partialId: Int?,
    @SerializedName("work_type_name")
    val workTypeName: String?,
    @SerializedName("evaluation_weight")
    val evaluationWeight: Int?
)

data class ResponseFormativeFieldBulk(
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int?,
    @SerializedName("name")
    val formativeFieldsName: String,
    @SerializedName("code")
    val formativeFieldsCode: String,
    @SerializedName("id")
    val formativeFieldsId: Int,
    @SerializedName("created_at")
    val createdAt: String
)
