package com.mx.liftechnology.data.repositoryImpl.evaluation

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.domain.model.evaluation.ModelWorkTypeByFormativeField
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError
import com.mx.liftechnology.domain.repository.evaluation.GetWorkTypeByFormativeFieldsRepository


class GetWorkTypeByFormativeFieldsRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
): GetWorkTypeByFormativeFieldsRepository {
    override suspend fun getByFormativeField(formativeFieldId: Int): ModelResult<ModelWorkTypeByFormativeField, NetworkModelError> {
        return formativeFieldApi.getWorkTypeByFormativeField(formativeFieldId).executeOrError { it.toData() }
    }
}