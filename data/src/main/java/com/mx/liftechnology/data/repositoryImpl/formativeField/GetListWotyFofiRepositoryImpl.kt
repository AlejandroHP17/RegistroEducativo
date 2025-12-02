package com.mx.liftechnology.data.repositoryImpl.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.domain.model.formativeFields.ModelWotyFofiDomain
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError
import com.mx.liftechnology.domain.repository.formativeFields.GetListWotyFofiRepository

class GetListWotyFofiRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
): GetListWotyFofiRepository {
    override suspend fun getList(schoolCycleId: Int): ModelResult<ModelWotyFofiDomain, NetworkModelError> {
        return formativeFieldApi.getListWotyFofi(schoolCycleId).executeOrError { it.toData() }
    }

}