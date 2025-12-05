package com.mx.liftechnology.domain.repository.auth

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.auth.LoginDomain
import com.mx.liftechnology.domain.model.auth.RegisterUserDomain
import com.mx.liftechnology.domain.model.auth.UserDomain

/**
 * Interfaz del repositorio para operaciones de autenticación.
 * Agrupa todas las operaciones relacionadas con autenticación: login, registro y obtención de datos de usuario.
 *
 * @author Pelkidev
 * @version 2.0.0
 */
interface AuthRepository {
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
        email: String,
        password: String,
        latitude: Double,
        longitude: Double,
        imei: String
    ): ModelResult<LoginDomain, NetworkModelError>

    /**
     * Realiza la petición de registro de usuario.
     *
     * @param email El correo electrónico del usuario.
     * @param pass La contraseña del usuario.
     * @param activationCode El código de activación.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun register(
        email: String,
        pass: String,
        activationCode: String
    ): ModelResult<RegisterUserDomain, NetworkModelError>

    /**
     * Obtiene los datos del usuario autenticado desde el servidor.
     *
     * @return Un [ModelResult] que contiene los datos del usuario en caso de éxito,
     *         o un [NetworkModelError] en caso de fallo.
     */
    suspend fun getData(): ModelResult<UserDomain, NetworkModelError>
}
