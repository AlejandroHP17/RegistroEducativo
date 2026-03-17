package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz agrupada para todas las operaciones de autenticación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ControlApi {
    /**
     * Realiza la petición a la API para el inicio de sesión.
     */
    @POST(Environment.END_POINT_CONTROL)
    suspend fun login(@Body request: RequestCode): Response<ResponseGeneric<ResponseCode>>
}

/**
 * Data class que representa la petición de inicio de sesión.
 *
 * @property code El codigo para registro de usuario.
 * @property accessLevelId El nivel del registro de usuario.
 * @property description Breve descripcion del codigo.*
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestCode(
    @SerializedName("code")
    val code: String,
    @SerializedName("access_level_id")
    val accessLevelId: Int,
    @SerializedName("description")
    val description: String
)

data class ResponseCode(
    @SerializedName("code")
    val code : String,
    @SerializedName("access_level_id")
    val accessLevelId : Int,
    @SerializedName("description")
    val description : String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("id")
    val id : Int,
    @SerializedName("created_by")
    val createdBy : Int,
    @SerializedName("created_at")
    val createdAt: String
)