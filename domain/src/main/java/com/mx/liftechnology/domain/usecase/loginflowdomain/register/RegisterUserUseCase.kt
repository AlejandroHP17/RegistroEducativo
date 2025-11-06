/**
 * @file Define el caso de uso para gestionar el registro de un nuevo usuario.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.loginflowdomain.register

import com.mx.liftechnology.core.network.apiCall.flowLogin.RequestRegisterUser
import com.mx.liftechnology.data.repository.flowLogin.register.RegisterUserRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.generic.ResultModel

/**
 * Caso de uso para gestionar el registro de un nuevo usuario.
 * Encapsula la lógica de negocio para crear una cuenta, interactuando con el repositorio correspondiente.
 *
 * @property registerUserRepository El repositorio para las operaciones de registro de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserUseCase(
    private val registerUserRepository: RegisterUserRepository
)  {
    /**
     * Ejecuta el proceso de registro de usuario.
     *
     * @param email El correo electrónico del usuario.
     * @param pass La contraseña del usuario.
     * @param activationCode El código de activación para la cuenta.
     * @return Un [ResultModel] que representa el resultado del intento de registro.
     */
    suspend operator fun invoke(email: String?, pass: String?, activationCode: String?): ModelResult<Boolean, Error> {
        if(email.isNullOrEmpty() || pass.isNullOrEmpty() || activationCode.isNullOrEmpty()) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        val request = RequestRegisterUser(
            email = email.lowercase(),
            password = pass,
            activationCode = activationCode
        )
        return runCatching{ registerUserRepository.executeRegisterUser(request) } .fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        if(result.data) SuccessResult(true)
                        else ErrorResult(NetworkError.UNKNOWN_REGISTER)
                    }
                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkError.UNKNOWN)}
        )
    }
}