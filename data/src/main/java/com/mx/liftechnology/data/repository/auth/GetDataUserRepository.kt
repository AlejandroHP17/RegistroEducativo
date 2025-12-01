package com.mx.liftechnology.data.repository.auth

import com.mx.liftechnology.core.network.api.AuthApi
import com.mx.liftechnology.data.mapper.AuthMapper.toData
import com.mx.liftechnology.data.model.auth.ModelGetUserData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

fun interface GetDataUserRepository{
    suspend fun getData(): ModelResult<ModelGetUserData, NetworkModelError>
}

class GetDataUserRepositoryImpl(
    private val authApi: AuthApi
) : GetDataUserRepository {

    override suspend fun getData(): ModelResult<ModelGetUserData, NetworkModelError> {
        return authApi.getUserData().executeOrError { it.toData() }
    }
}