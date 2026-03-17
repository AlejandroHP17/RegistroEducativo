package com.mx.liftechnology.data.repositoryImpl.control

import com.mx.liftechnology.core.network.api.ControlApi
import com.mx.liftechnology.core.network.api.RequestCode
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.ControlMapper.toNewCodeDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.control.NewCodeDomain
import com.mx.liftechnology.domain.repository.auth.AuthRepository
import com.mx.liftechnology.domain.repository.control.ControlRepository

/**
 * Implementación de [AuthRepository].
 * Se encarga de realizar las llamadas a la API y de gestionar las respuestas de éxito y error
 * para todas las operaciones relacionadas con autenticación.
 *
 * @property controlApi La llamada a la API para operaciones de autenticación.
 * @author Pelkidev
 * @version 1.0.0
 */
class ControlRepositoryImpl(
    private val controlApi: ControlApi,
) : ControlRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun newCode(
        code: String,
        accessLevelId: Int,
        description: String
    ): ModelResult<NewCodeDomain, NetworkModelError> {
       val request = RequestCode(
           code = code,
           accessLevelId = accessLevelId,
           description = description
       )

        return safeApiCall(
            apiCall = { controlApi.login(request) },
            mapper = { it.toNewCodeDomain() }
        )
    }
}
