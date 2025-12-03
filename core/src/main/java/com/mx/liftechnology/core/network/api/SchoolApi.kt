package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interfaz agrupada para todas las operaciones relacionadas con escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface SchoolApi {
    /**
     * Obtiene información de una escuela por CCT.
     */
    @GET(Environment.END_POINT_GET_CCT)
    suspend fun getCct(@Path("cct") cct: String): Response<ResponseGeneric<ResponseCctSchool>>
}

/**
 * Data class que representa la información de una escuela obtenida por su CCT (Clave de Centro de Trabajo).
 *
 * @property cct La clave de centro de trabajo de la escuela.
 * @property schoolTypeId El ID del tipo de escuela.
 * @property schoolName El nombre de la escuela.
 * @property postalCode El código postal de la escuela.
 * @property latitude La latitud de la ubicación de la escuela.
 * @property longitude La longitud de la ubicación de la escuela.
 * @property shiftId El ID del turno de la escuela.
 * @property shiftName El nombre del turno de la escuela.
 * @property schoolId El ID único de la escuela.
 * @property createdAt La fecha y hora de creación del registro.
 * @property periodCatalog El catálogo de periodos disponibles para la escuela.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseCctSchool(
    @SerializedName("cct")
    val cct: String,
    @SerializedName("school_type_id")
    val schoolTypeId: Int,
    @SerializedName("name")
    val schoolName: String,
    @SerializedName("postal_code")
    val postalCode: String?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?,
    @SerializedName("shift_id")
    val shiftId: Int?,
    @SerializedName("shift_name")
    val shiftName: String?,
    @SerializedName("id")
    val schoolId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("period_catalog")
    val periodCatalog: List<ResponsePeriodCatalog>?
)

/**
 * Data class que representa un periodo del catálogo de periodos de una escuela.
 *
 * @property id El ID único del periodo.
 * @property typeName El nombre del tipo de periodo.
 * @property periodNumber El número del periodo.
 * @property createdAt La fecha y hora de creación del registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponsePeriodCatalog(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type_name")
    val typeName: String,
    @SerializedName("period_number")
    val periodNumber: Int,
    @SerializedName("created_at")
    val createdAt: String
)