package com.mx.liftechnology.core.network.api

import com.google.gson.annotations.SerializedName
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interfaz agrupada para todas las operaciones de autenticación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface AuthApi {
    /**
     * Realiza la petición a la API para el inicio de sesión.
     */
    @POST(Environment.END_POINT_LOGIN)
    suspend fun login(@Body request: RequestLogin): Response<ResponseGeneric<ResponseLogin>>

    /**
     * Realiza la petición a la API para el registro de usuarios.
     */
    @POST(Environment.END_POINT_REGISTER)
    suspend fun register(@Body request: RequestRegisterUser): Response<ResponseGeneric<ResponseRegisterUser>>

    /**
     * Realiza la petición a la API para obtener datos de usuario.
     */
    @GET(Environment.END_POINT_GET_DATA)
    suspend fun getUserData(): Response<ResponseGeneric<ResponseDataUser>>
}

/**
 * Data class que representa la petición de inicio de sesión.
 *
 * @property email El correo electrónico del usuario.
 * @property password La contraseña del usuario.
 * @property latitude La latitud de la ubicación del dispositivo.
 * @property longitude La longitud de la ubicación del dispositivo.
 * @property imei El identificador único del dispositivo (IMEI o equivalente).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestLogin(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("imei")
    val imei: String
)

/**
 * Data class que representa la respuesta del servidor después de un inicio de sesión exitoso.
 *
 * @property accessToken El token de acceso para autenticar peticiones posteriores.
 * @property refreshToken El token de refresco para renovar el access token cuando expire.
 * @property tokenType El tipo de token (generalmente "Bearer").
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseLogin(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String?,
)

/**
 * Data class que representa la petición para registrar un nuevo usuario.
 *
 * @property email El correo electrónico del usuario.
 * @property password La contraseña del usuario.
 * @property activationCode El código de activación requerido para el registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RequestRegisterUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("access_code")
    val activationCode: String
)

/**
 * Data class que representa la respuesta del servidor después de registrar un usuario.
 *
 * @property email El correo electrónico del usuario registrado.
 * @property firstName El nombre del usuario.
 * @property lastName El apellido del usuario.
 * @property phone El número de teléfono del usuario.
 * @property accessLevel El nivel de acceso asignado al usuario.
 * @property accessCode El ID del código de acceso utilizado.
 * @property isActive Indica si la cuenta del usuario está activa.
 * @property id El ID único del usuario generado por el servidor.
 * @property createdAt La fecha y hora de creación del registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseRegisterUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("access_level_id")
    val accessLevel: Int,
    @SerializedName("access_code_id")
    val accessCode: Int?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("created_at")
    val createdAt: String
)

/**
 * Data class que representa los datos del usuario obtenidos del servidor.
 *
 * @property email El correo electrónico del usuario.
 * @property name El nombre del usuario.
 * @property lastName El apellido del usuario.
 * @property phone El número de teléfono del usuario.
 * @property accessLevelId El ID del nivel de acceso del usuario.
 * @property accessCodeId El ID del código de acceso del usuario.
 * @property isActive Indica si la cuenta del usuario está activa.
 * @property userId El ID único del usuario.
 * @property createdAt La fecha y hora de creación del registro.
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