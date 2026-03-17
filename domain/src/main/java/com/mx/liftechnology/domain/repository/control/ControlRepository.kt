package com.mx.liftechnology.domain.repository.control

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.control.NewCodeDomain

interface ControlRepository {

    suspend fun newCode(
        code: String,
        accessLevelId: Int,
        description: String
    ): ModelResult<NewCodeDomain, NetworkModelError>
}