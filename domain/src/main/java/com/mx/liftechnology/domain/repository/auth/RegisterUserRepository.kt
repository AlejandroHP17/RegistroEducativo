package com.mx.liftechnology.domain.repository.auth

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.auth.RegisterUserDomain

/**
 * Interfaz del repositorio para el registro de usuarios.
 * Define el contrato para ejecutar la lógica de registro.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterUserRepository {
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
}