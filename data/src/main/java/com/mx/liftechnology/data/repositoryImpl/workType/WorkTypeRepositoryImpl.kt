package com.mx.liftechnology.data.repositoryImpl.workType

import com.mx.liftechnology.core.network.api.WorkTypeApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.WorkTypeMapper.toWorkTypeByFormativeFieldDomain
import com.mx.liftechnology.data.mapper.WorkTypeMapper.toWorkTypeDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import com.mx.liftechnology.domain.repository.workType.WorkTypeRepository

class WorkTypeRepositoryImpl(
    private val workTypeApi: WorkTypeApi
): WorkTypeRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getWorkTypeByFormativeField(formativeFieldId: Int): ModelResult<WorkTypeByFormativeFieldDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { workTypeApi.getWorkTypeByFormativeField(formativeFieldId) },
            mapper = { it.toWorkTypeByFormativeFieldDomain() }
        )
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun getWorkTypeList(teacherId: Int): ModelResult<List<WorkTypeDomain>, NetworkModelError> {
        return safeApiCall(
            apiCall = { workTypeApi.getListWorkType(teacherId) },
            mapper = { it.toWorkTypeDomain() }
        )
    }
}