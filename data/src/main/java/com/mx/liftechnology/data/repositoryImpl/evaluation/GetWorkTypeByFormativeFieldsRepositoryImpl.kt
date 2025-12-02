package com.mx.liftechnology.data.repositoryImpl.evaluation

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.repository.evaluation.GetWorkTypeByFormativeFieldsRepository


class GetWorkTypeByFormativeFieldsRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
): GetWorkTypeByFormativeFieldsRepository {
    override suspend fun getByFormativeField(formativeFieldId: Int): ModelResult<WorkTypeByFormativeFieldDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { formativeFieldApi.getWorkTypeByFormativeField(formativeFieldId) },
            mapper = { it.toData() }
        )
    }
}