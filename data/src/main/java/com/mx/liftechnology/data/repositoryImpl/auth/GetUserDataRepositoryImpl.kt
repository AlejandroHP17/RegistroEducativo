package com.mx.liftechnology.data.repositoryImpl.auth

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.AuthMapper.toData
import com.mx.liftechnology.data.util.executeOrError
import com.mx.liftechnology.domain.model.auth.UserDomain
import com.mx.liftechnology.domain.repository.auth.GetDataUserRepository


class GetDataUserRepositoryImpl(
    private val authApi: AuthApi
) : GetDataUserRepository {

    override suspend fun getData(): ModelResult<UserDomain, NetworkModelError> {
        return authApi.getUserData().executeOrError { it.toData() }
    }
}