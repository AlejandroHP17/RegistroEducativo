package com.mx.liftechnology.data.repository.flowMain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterStudent
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterStudentRepository{
    suspend fun executeRegisterOneStudent(request: RequestRegisterStudent)
    : ResultService<List<String?>?, FailureService>
}

class RegisterStudentRepositoryImp(
    private val registerStudentApiCall: RegisterStudentApiCall,
) : RegisterStudentRepository {

    override suspend fun executeRegisterOneStudent(
        request: RequestRegisterStudent
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