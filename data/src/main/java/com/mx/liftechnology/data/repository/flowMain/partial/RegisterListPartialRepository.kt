package com.mx.liftechnology.data.repository.flowMain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListPartialApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterPartial
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterListPartialRepository{
  suspend fun executeRegisterListPartial(request : RequestRegisterPartial
  ): ResultService<List<String?>?, FailureService>
}

class RegisterListPartialRepositoryImp(
    private val registerListPartialApiCall: RegisterListPartialApiCall,
) : RegisterListPartialRepository {

    override suspend fun executeRegisterListPartial(
        request : RequestRegisterPartial
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerListPartialApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}