package com.mx.liftechnology.data.repositoryImpl.school

import com.mx.liftechnology.core.network.api.SchoolApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.SchoolMapper.toCCTDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.schoolCycle.CCTDomain
import com.mx.liftechnology.domain.repository.school.SchoolRepository

/**
 * Implementación de [SchoolRepository].
 * Se encarga de realizar las llamadas a la API y de gestionar las respuestas de éxito y error
 * para todas las operaciones relacionadas con escuelas.
 *
 * @property schoolApi La llamada a la API para operaciones de escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */
class SchoolRepositoryImpl(
    private val schoolApi: SchoolApi
) : SchoolRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getCct(cct: String): ModelResult<CCTDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { schoolApi.getCct(cct) },
            mapper = { it.toCCTDomain() }
        )
    }
}
