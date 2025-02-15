package com.mx.liftechnology.data.repository.registerFlow

import com.mx.liftechnology.core.network.callapi.CredentialsRegisterStudent
import com.mx.liftechnology.core.network.callapi.RegisterStudentApiCall
import com.mx.liftechnology.core.network.util.ExceptionHandler
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultService
import com.mx.liftechnology.core.network.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterStudentRepository{
  suspend fun executeRegisterStudent(request: CredentialsRegisterStudent): ResultService<List<String?>?, FailureService>
}

class RegisterStudentRepositoryImp(
    private val registerStudentApiCall: RegisterStudentApiCall
) : RegisterStudentRepository {

    override suspend fun executeRegisterStudent(
        request: CredentialsRegisterStudent
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerStudentApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}