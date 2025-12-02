package com.mx.liftechnology.domain.repository.auth

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.auth.LoginDomain


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
    ): ModelResult<LoginDomain, NetworkModelError>
}