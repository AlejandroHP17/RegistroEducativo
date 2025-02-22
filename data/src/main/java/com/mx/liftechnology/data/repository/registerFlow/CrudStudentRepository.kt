package com.mx.liftechnology.data.repository.registerFlow

import com.mx.liftechnology.core.network.callapi.CredentialGetListStudent
import com.mx.liftechnology.core.network.callapi.CredentialsRegisterStudent
import com.mx.liftechnology.core.network.callapi.GetListStudentApiCall
import com.mx.liftechnology.core.network.callapi.RegisterStudentApiCall
import com.mx.liftechnology.core.network.callapi.ResponseGetStudent
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

interface CrudStudentRepository{
    suspend fun executeRegisterStudent(request: CredentialsRegisterStudent): ResultService<List<String?>?, FailureService>
    suspend fun executeGetListStudent(request: CredentialGetListStudent): ResultService<List<ResponseGetStudent?>?, FailureService>
}

class CrudStudentRepositoryImp(
    private val registerStudentApiCall: RegisterStudentApiCall,
    private val getListStudentApiCall : GetListStudentApiCall
) : CrudStudentRepository {

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

    override suspend fun executeGetListStudent(
        request: CredentialGetListStudent
    ) : ResultService<List<ResponseGetStudent?>?, FailureService> {
        return try {
            val response = getListStudentApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}