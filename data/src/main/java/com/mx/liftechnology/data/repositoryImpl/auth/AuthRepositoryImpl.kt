package com.mx.liftechnology.data.repositoryImpl.auth

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.core.network.api.RequestLogin
import com.mx.liftechnology.core.network.api.RequestRegisterUser
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.AuthMapper.toLoginDomain
import com.mx.liftechnology.data.mapper.AuthMapper.toRegisterUserDomain
import com.mx.liftechnology.data.mapper.AuthMapper.toUserDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.auth.LoginDomain
import com.mx.liftechnology.domain.model.auth.RegisterUserDomain
import com.mx.liftechnology.domain.model.auth.UserDomain
import com.mx.liftechnology.domain.repository.auth.AuthRepository

/**
 * Implementación de [AuthRepository].
 * Se encarga de realizar las llamadas a la API y de gestionar las respuestas de éxito y error
 * para todas las operaciones relacionadas con autenticación.
 *
 * @property authApi La llamada a la API para operaciones de autenticación.
 * @author Pelkidev
 * @version 2.0.0
 */
class AuthRepositoryImpl(
    private val authApi: AuthApi,
) : AuthRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun login(
        email: String,
        password: String,
        latitude: Double,
        longitude: Double,
        imei: String
    ): ModelResult<LoginDomain, NetworkModelError> {
        val request = RequestLogin(
            email = email,
            password = password,
            latitude = latitude,
            longitude = longitude,
            imei = imei
        )

        return safeApiCall(
            apiCall = { authApi.login(request) },
            mapper = { it.toLoginDomain() }
        )
    }

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

    /**
     * {@inheritDoc}
     */
    override suspend fun getData(): ModelResult<UserDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { authApi.getUserData() },
            mapper = { it.toUserDomain() }
        )
    }
}
