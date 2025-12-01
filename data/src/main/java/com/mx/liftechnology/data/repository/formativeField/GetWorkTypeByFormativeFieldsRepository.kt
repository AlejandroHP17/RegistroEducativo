package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeByFormativeField
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

fun interface GetWorkTypeByFormativeFieldsRepository {
    suspend fun getByFormativeField(formativeFieldId: Int): ModelResult<ModelWorkTypeByFormativeField, NetworkModelError>
}
class GetWorkTypeByFormativeFieldsRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
): GetWorkTypeByFormativeFieldsRepository{
    override suspend fun getByFormativeField(formativeFieldId: Int): ModelResult<ModelWorkTypeByFormativeField, NetworkModelError> {
        return formativeFieldApi.getWorkTypeByFormativeField(formativeFieldId).executeOrError { it.toData() }
    }
}