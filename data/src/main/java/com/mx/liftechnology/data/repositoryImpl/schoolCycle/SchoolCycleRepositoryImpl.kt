package com.mx.liftechnology.data.repositoryImpl.schoolCycle

import com.mx.liftechnology.core.network.api.RequestRegisterSchoolCycle
import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toRegisterSchoolCycleDomain
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toSchoolCycleDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain
import com.mx.liftechnology.domain.model.schoolCycle.SchoolCycleDomain
import com.mx.liftechnology.domain.repository.menu.MenuRepository
import com.mx.liftechnology.domain.repository.schoolCycle.SchoolCycleRepository

/**
 * Implementación de [SchoolCycleRepository] y [MenuRepository].
 * Se encarga de realizar las llamadas a la API y de gestionar las respuestas de éxito y error
 * para todas las operaciones relacionadas con ciclos escolares y menú.
 *
 * @property schoolCycleApi La llamada a la API para operaciones de ciclos escolares.
 * @author Pelkidev
 * @version 2.0.0
 */
class SchoolCycleRepositoryImpl(
    private val schoolCycleApi: SchoolCycleApi
) : SchoolCycleRepository, MenuRepository {
    /**
     * {@inheritDoc}
     */
    override suspend fun getCycleSchool(teacherId: Int): ModelResult<List<SchoolCycleDomain>, NetworkModelError> {
        val result = safeApiCall(
            apiCall = { schoolCycleApi.getGroup(teacherId = teacherId) },
            mapper = { it.toSchoolCycleDomain() }
        )
        return when (result) {
            is SuccessResult -> {
                if (result.data.isNotEmpty()) {
                    result
                } else {
                    ErrorResult(NetworkModelError.EMPTY)
                }
            }
            is ErrorResult -> result
        }
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun registerCycleSchool(
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