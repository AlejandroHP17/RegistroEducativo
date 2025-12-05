package com.mx.liftechnology.data.repositoryImpl.schoolCycle

import com.mx.liftechnology.core.network.api.RequestRegisterSchoolCycle
import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toRegisterSchoolCycleDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain
import com.mx.liftechnology.domain.repository.school.RegisterCycleSchoolRepository

/**
 * @file Define el repositorio para la funcionalidad de registro de escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */
/**
 * Implementación de [RegisterCycleSchoolRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerSchoolCycleApiCall La llamada a la API para el registro de escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterCycleSchoolRepositoryImpl(
    private val schoolCycleApi: SchoolCycleApi
) : RegisterCycleSchoolRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun register(
        teacherId : Int,
        schoolId : Int,
        name : String,
        cycleLabel : String,
        grade : String,
        nameGroup : String,
        periodCatalogId : Int
    ): ModelResult<RegisterSchoolCycleDomain, NetworkModelError> {
        val request = RequestRegisterSchoolCycle(
            teacherId = teacherId,
            schoolId = schoolId,
            name = name,
            cycleLabel = "",
            grade = grade,
            nameGroup = nameGroup,
            periodCatalogId = periodCatalogId,
            isActive = true
        )

        return safeApiCall(
            apiCall = { schoolCycleApi.registerSchoolCycle(request) },
            mapper = { it.toRegisterSchoolCycleDomain() }
        )
    }
}