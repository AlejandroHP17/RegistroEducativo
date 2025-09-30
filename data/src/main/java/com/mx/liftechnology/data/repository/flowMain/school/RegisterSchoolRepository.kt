package com.mx.liftechnology.data.repository.flowMain.school

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSchoolApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSchool
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterSchoolRepository{
  suspend fun executeRegisterOneSchool(
      request : RequestRegisterSchool
  ): ResultService<List<String?>?, FailureService>
}

class RegisterSchoolRepositoryImp(
    private val registerSchoolApiCall: RegisterSchoolApiCall
) : RegisterSchoolRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegisterOneSchool(
        request : RequestRegisterSchool
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerSchoolApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}