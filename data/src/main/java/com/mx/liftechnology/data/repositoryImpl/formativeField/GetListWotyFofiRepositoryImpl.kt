package com.mx.liftechnology.data.repositoryImpl.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toWotyFofiDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.repository.formativeFields.GetListWotyFofiRepository

class GetListWotyFofiRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
): GetListWotyFofiRepository {
    override suspend fun getList(schoolCycleId: Int): ModelResult<WotyFofiDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { formativeFieldApi.getListWotyFofi(schoolCycleId) },
            mapper = { it.toWotyFofiDomain() }
        )
    }

}