package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz agrupada para todas las operaciones relacionadas con tipos de trabajo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface WorkTypeApi {
    /**
     * Obtiene la lista de tipos de trabajo.
     */
    @GET(Environment.END_POINT_GET_WORK_TYPE)
    suspend fun getListWorkType(@Query("teacher_id") teacherId: Int): Response<ResponseGeneric<List<ResponseGetListWorkType>>>

    /**
     * Obtiene el tipo de trabajo por campo formativo.
     */
    @GET(Environment.END_POINT_GET_WORK_TYPE_BY)
    suspend fun getWorkTypeByFormativeField(@Path("formative_field_id") formativeFieldId: Int): Response<ResponseGeneric<ResponseGetWorkType>>

}

/**
 * Data class que representa un tipo de trabajo en la lista.
 *
 * @property workTypeId El ID único del tipo de trabajo.
 * @property name El nombre del tipo de trabajo.
 * @property teacherId El ID del profesor que creó el tipo de trabajo.
 * @property createAt La fecha y hora de creación del tipo de trabajo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetListWorkType(
    @SerializedName("id")
    val workTypeId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("teacher_id")
    val teacherId: Int,
    @SerializedName("created_at")
    val createAt: String
)

/**
 * Data class que representa la respuesta con tipos de trabajo agrupados por campo formativo.
 *
 * @property formativeFieldName El nombre del campo formativo.
 * @property formativeFieldId El ID del campo formativo.
 * @property workTypes La lista de tipos de trabajo asociados al campo formativo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetWorkType(
    @SerializedName("formative_field_name")
    val formativeFieldName: String,
    @SerializedName("formative_field_id")
    val formativeFieldId: Int,
    @SerializedName("work_types")
    val workTypes: List<ResponseWorkTypeDetail>
)

/**
 * Data class que representa los detalles de un tipo de trabajo.
 *
 * @property workTypeName El nombre del tipo de trabajo.
 * @property workTypeId El ID único del tipo de trabajo.
 * @property evaluationWeight El peso de evaluación del tipo de trabajo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseWorkTypeDetail(
    @SerializedName("work_type_name")
    val workTypeName: String,
    @SerializedName("work_type_id")
    val workTypeId: Int,
    @SerializedName("evaluation_weight")
    val evaluationWeight: String
)

