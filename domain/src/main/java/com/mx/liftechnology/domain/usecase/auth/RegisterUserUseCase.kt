package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.repository.auth.AuthRepository


/**
 * Caso de uso para gestionar el registro de un nuevo usuario.
 * Encapsula la lógica de negocio para crear una cuenta, interactuando con el repositorio correspondiente.
 *
 * @property authRepository El repositorio para las operaciones de autenticación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserUseCase(
    private val authRepository: AuthRepository
)  {
    /**
     * Ejecuta el proceso de registro de usuario.
     *
     * @param email El correo electrónico del usuario.
     * @param pass La contraseña del usuario.
     * @param activationCode El código de activación para la cuenta.
     * @return Un [ModelResult] que representa el resultado del intento de registro.
     */
    suspend operator fun invoke(email: String?, pass: String?, activationCode: String?): ModelResult<Boolean, ModelError> {
        if(email.isNullOrEmpty() || pass.isNullOrEmpty() || activationCode.isNullOrEmpty()) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        val result = authRepository.register(
            email = email,
            pass = pass,
            activationCode = activationCode
        )
        return when (result) {
            is SuccessResult -> SuccessResult(true)
            is ErrorResult -> result
        }
    }
}