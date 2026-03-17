package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseCode
import com.mx.liftechnology.domain.model.control.NewCodeDomain

object ControlMapper {

    fun ResponseCode.toNewCodeDomain(): NewCodeDomain {
        return NewCodeDomain(
            code = code,
            accessLevelId = accessLevelId,
            description = description
        )

    }
}