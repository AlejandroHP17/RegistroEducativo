package com.mx.liftechnology.domain.repository.formativeFields

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.formativeFields.ModelWotyFofiDomain


fun interface GetListWotyFofiRepository {
    suspend fun getList(schoolCycleId: Int): ModelResult<ModelWotyFofiDomain, NetworkModelError>
}
