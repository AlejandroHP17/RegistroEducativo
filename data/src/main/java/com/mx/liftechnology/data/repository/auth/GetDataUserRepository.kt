package com.mx.liftechnology.data.repository.auth

import com.mx.liftechnology.core.network.apiCall.auth.GetDataUserApiCall
import com.mx.liftechnology.data.mapper.AuthDataToDomainMapper.mapperToGetUserData
import com.mx.liftechnology.data.model.auth.ModelGetUserData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface GetDataUserRepository{
    suspend fun executeGetData(): ModelResult<ModelGetUserData, NetworkModelError>
}

class GetDataUserRepositoryImpl(
    private val getDataUserApiCall: GetDataUserApiCall
) : GetDataUserRepository {

    override suspend fun executeGetData(): ModelResult<ModelGetUserData, NetworkModelError> {
        return try {
            val response = getDataUserApiCall.callApi()
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToGetUserData())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}