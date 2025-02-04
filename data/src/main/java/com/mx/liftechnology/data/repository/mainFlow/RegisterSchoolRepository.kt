package com.mx.liftechnology.data.repository.mainFlow

import com.mx.liftechnology.core.network.callapi.CredentialsRegisterSchool
import com.mx.liftechnology.core.network.callapi.RegisterSchoolApiCall
import com.mx.liftechnology.core.network.util.ExceptionHandler
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultService
import com.mx.liftechnology.core.network.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterSchoolRepository{
  suspend fun executeRegisterSchool(
      request : CredentialsRegisterSchool
  ): ResultService<List<String?>?, FailureService>
}

class RegisterSchoolRepositoryImp(
    private val registerApiCall: RegisterSchoolApiCall
) :  RegisterSchoolRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegisterSchool(
        request : CredentialsRegisterSchool
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }



}