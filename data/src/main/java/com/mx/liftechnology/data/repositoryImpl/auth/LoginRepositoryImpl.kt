/**
 * @file Define el repositorio para la funcionalidad de inicio de sesión.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.auth

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.core.network.api.RequestLogin
import com.mx.liftechnology.domain.model.auth.LoginDomain
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.toData
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.repository.auth.LoginRepository

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
            mapper = { it.toData() }
        )
    }
}

