package com.mx.liftechnology.data.repositoryImpl.auth

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.core.network.api.RequestRegisterUser
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.AuthMapper.toRegisterUserDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.auth.RegisterUserDomain
import com.mx.liftechnology.domain.repository.auth.RegisterUserRepository


/**
 * Implementación de [RegisterUserRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property AuthApi La llamada a la API para el registro de usuarios.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserRepositoryImpl(
    private val authApi: AuthApi
) : RegisterUserRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun register(
        email: String,
        pass: String,
        activationCode: String
    ): ModelResult<RegisterUserDomain, NetworkModelError> {
        val request = RequestRegisterUser(
            email = email.lowercase(),
            password = pass,
            activationCode = activationCode
        )

        return safeApiCall(
            apiCall = { authApi.register(request) },
            mapper = { it.toRegisterUserDomain() }
        )
    }
}