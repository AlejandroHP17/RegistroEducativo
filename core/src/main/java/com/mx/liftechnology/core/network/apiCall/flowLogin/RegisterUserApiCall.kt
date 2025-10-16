/**
 * @file Define la llamada a la API para el registro de usuarios y los modelos de datos asociados.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.network.apiCall.flowLogin

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.enviroment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz para la llamada a la API de registro de usuarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterUserApiCall {
    /**
     * Realiza la petición a la API para el registro de usuarios.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @POST(Environment.END_POINT_REGISTER)
    suspend fun callApi(
        @Body request: RequestRegisterUser
    ): Response<ResponseGeneric<List<String?>?>?>
}

/**
 * Modelo de datos para la petición de registro de usuarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("codigoactivacion")
    val activationCode: String
)