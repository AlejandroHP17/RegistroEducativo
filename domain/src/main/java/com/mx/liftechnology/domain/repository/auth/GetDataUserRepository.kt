package com.mx.liftechnology.domain.repository.auth

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.auth.UserDomain


fun interface GetDataUserRepository{
    suspend fun getData(): ModelResult<UserDomain, NetworkModelError>
}
