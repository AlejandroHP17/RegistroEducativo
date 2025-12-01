/**
 * @file Define el repositorio para la funcionalidad de inicio de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.auth

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.core.network.api.RequestLogin
import com.mx.liftechnology.data.mapper.AuthMapper.toData
import com.mx.liftechnology.data.model.auth.LoginData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para el inicio de sesión.
 * Define el contrato para ejecutar la lógica de inicio de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface LoginRepository {
    /**
     * Realiza la petición de inicio de sesión.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @param latitude La latitud de la ubicación.
     * @param longitude La longitud de la ubicación.
     * @param imei El identificador del dispositivo.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun login(
        email : String,
        password : String,
        latitude : Double,
        longitude : Double,
        imei : String
    ): ModelResult<LoginData, NetworkModelError>
}

/**
 * Implementación de [LoginRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property loginApiCall La llamada a la API para el inicio de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginRepositoryImpl(
    private val authApi: AuthApi,
) : LoginRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun login(
        email : String,
        password : String,
        latitude : Double,
        longitude : Double,
        imei : String
    ): ModelResult<LoginData, NetworkModelError> {
        val request = RequestLogin(
            email = email,
            password = password,
            latitude = latitude,
            longitude = longitude,
            imei = imei
        )

        return authApi.login(request).executeOrError { it.toData() }
    }
}

