/**
 * @file Define la llamada a la API para el registro de escuelas y los modelos de datos asociados.
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
 * Interfaz para la llamada a la API de registro de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterSchoolApiCall {
    /**
     * Realiza la petición a la API para registrar una escuela.
     *
     * @param request Los datos de la petición.
     * @return Una respuesta de Retrofit que contiene un [ResponseGeneric] con una lista de strings.
     */
    @POST(Environment.END_POINT_REGISTER_SCHOOL)
    suspend fun callApi(
        @Body request: RequestRegisterSchool
    ): Response<ResponseGeneric<List<String?>?>>
}

/**
 * Modelo de datos para la petición de registro de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterSchool(
    @SerializedName("cct")
    val cct: String?,
    @SerializedName("tipocicloescolar_id")
    val typeCycleSchoolId: Int?,
    @SerializedName("grado")
    val grade: Int?,
    @SerializedName("nombregrupo")
    val nameGroup: String?,
    @SerializedName("anio")
    val year: String?,
    @SerializedName("periodo")
    val period: Int?,
    @SerializedName("profesor_id")
    val teacherId: Int?,
    @SerializedName("user_id")
    val userId: Int?
)