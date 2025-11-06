package com.mx.liftechnology.data.repository.flowLogin.login

import com.mx.liftechnology.core.network.apiCall.flowLogin.GetDataUserApiCall
import com.mx.liftechnology.core.network.apiCall.flowLogin.ResponseUserData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface GetDataUserRepository{
    suspend fun executeGetData(): ModelResult<ResponseUserData, NetworkError>
}

class GetDataUserRepositoryImpl(
    private val getDataUserApiCall: GetDataUserApiCall
) : GetDataUserRepository {

    override suspend fun executeGetData(): ModelResult<ResponseUserData, NetworkError> {
        return try {
            val response = getDataUserApiCall.callApi()
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it)
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkError.UNKNOWN)
        }
    }
}