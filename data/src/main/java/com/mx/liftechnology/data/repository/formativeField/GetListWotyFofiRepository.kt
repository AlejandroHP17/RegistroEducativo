package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.data.model.formativeField.ModelWotyFofiData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

fun interface GetListWotyFofiRepository {
    suspend fun getList(schoolCycleId: Int): ModelResult<ModelWotyFofiData, NetworkModelError>
}

class GetListWotyFofiRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
):GetListWotyFofiRepository{
    override suspend fun getList(schoolCycleId: Int): ModelResult<ModelWotyFofiData, NetworkModelError> {
        return formativeFieldApi.getListWotyFofi(schoolCycleId).executeOrError { it.toData() }
    }

}