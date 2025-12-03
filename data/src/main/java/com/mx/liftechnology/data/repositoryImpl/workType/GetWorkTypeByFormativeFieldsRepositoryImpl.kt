package com.mx.liftechnology.data.repositoryImpl.workType

import com.mx.liftechnology.core.network.api.WorkTypeApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.repository.evaluation.GetWorkTypeByFormativeFieldsRepository

class GetWorkTypeByFormativeFieldsRepositoryImpl(
    private val workTypeApi: WorkTypeApi
): GetWorkTypeByFormativeFieldsRepository {
    override suspend fun getByFormativeField(formativeFieldId: Int): ModelResult<WorkTypeByFormativeFieldDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { workTypeApi.getWorkTypeByFormativeField(formativeFieldId) },
            mapper = { it.toData() }
        )
    }
}