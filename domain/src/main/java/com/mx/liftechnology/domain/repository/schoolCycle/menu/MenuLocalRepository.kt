package com.mx.liftechnology.domain.repository.schoolCycle.menu

import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.domain.repository.schoolCycle.PrincipalMenuDomain

interface MenuLocalRepository {

    suspend fun getControlMenu(
    ): ModelResult<List<PrincipalMenuDomain>, ModelError>

    suspend fun getControlRegister(
    ): ModelResult<List<PrincipalMenuDomain>, ModelError>
}