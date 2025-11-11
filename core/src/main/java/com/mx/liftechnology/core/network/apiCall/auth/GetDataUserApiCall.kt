package com.mx.liftechnology.core.network.apiCall.auth

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interfaz para la llamada a la API de obtener datos de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetDataUserApiCall {
    /**
     * Realiza la petición a la API para obtener datos de usuario.
     *
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con los datos de [ResponseDataUser].
     */
    @GET(Environment.END_POINT_GET_DATA)
    suspend fun callApi(): Response<ResponseGeneric<ResponseDataUser>>
}

/**
 * Modelo de datos para la información del usuario en la respuesta de obtener los datos del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseDataUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val name: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("access_level_id")
    val accessLevelId: Int?,
    @SerializedName("access_code_id")
    val accessCodeId: Int,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("id")
    val userId: Int,
    @SerializedName("created_at")
    val createdAt: String,
)