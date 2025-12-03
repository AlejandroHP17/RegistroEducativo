package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Interfaz agrupada para todas las operaciones relacionadas con parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface PartialApi {
    /**
     * Obtiene la lista de parciales.
     */
    @GET(Environment.END_POINT_GET_PARTIAL)
    suspend fun getListPartial(@Query("school_cycle_id") schoolCycleId: Int): Response<ResponseGeneric<List<ResponseGetPartials>>>


    /**
     * Registra una lista de parciales.
     */
    @POST(Environment.END_POINT_REGISTER_PARTIAL)
    suspend fun registerListPartial(@Body request: RequestRegisterPartial): Response<ResponseGeneric<List<ResponseRegisterPartial>>>

}

/**
 * Data class que representa un parcial obtenido del ciclo escolar.
 *
 * @property schoolCycleId El ID del ciclo escolar al que pertenece el parcial.
 * @property description El nombre o descripción del parcial.
 * @property startDate La fecha de inicio del parcial.
 * @property endDate La fecha de fin del parcial.
 * @property partialId El ID único del parcial.
 * @property createdAt La fecha y hora de creación del registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseGetPartials(
    @SerializedName("school_cycle_id")
    val schoolCycleId: Int,
    @SerializedName("name")
    val description: String,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("id")
    val partialId: Int,
    @SerializedName("created_at")
    val createdAt: String
)

/**
 * Data class que representa la petición para registrar múltiples parciales.
 *
 * @property listPartials La lista de parciales a registrar.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterPartial(
    @SerializedName("partials")
    val listPartials: List<RequestPartials?>
)

/**
 * Data class que representa un parcial individual en la petición de registro.
 *
 * @property cycleSchoolId El ID del ciclo escolar al que pertenece el parcial.
 * @property description El nombre o descripción del parcial.
 * @property startDate La fecha de inicio del parcial.
 * @property endDate La fecha de fin del parcial.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestPartials(
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int?,
    @SerializedName("name")
    val description: String?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?
)

/**
 * Data class que representa la respuesta del servidor después de registrar un parcial.
 *
 * @property cycleSchoolId El ID del ciclo escolar al que pertenece el parcial.
 * @property description El nombre o descripción del parcial.
 * @property startDate La fecha de inicio del parcial.
 * @property endDate La fecha de fin del parcial.
 * @property partialId El ID único del parcial generado por el servidor.
 * @property createdAt La fecha y hora de creación del registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseRegisterPartial(
    @SerializedName("school_cycle_id")
    val cycleSchoolId: Int?,
    @SerializedName("name")
    val description: String,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("id")
    val partialId: Int,
    @SerializedName("created_at")
    val createdAt: String?
)

