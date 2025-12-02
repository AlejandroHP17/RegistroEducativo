package com.mx.liftechnology.domain.repository.evaluation

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.evaluation.ModelWorkTypeByFormativeField

fun interface GetWorkTypeByFormativeFieldsRepository {
    suspend fun getByFormativeField(formativeFieldId: Int): ModelResult<ModelWorkTypeByFormativeField, NetworkModelError>
}