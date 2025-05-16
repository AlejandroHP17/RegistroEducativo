package com.mx.liftechnology.data.repository.mainflowdata.school

import com.mx.liftechnology.core.network.callapi.CredentialsRegisterSchool
import com.mx.liftechnology.core.network.callapi.RegisterOneSchoolApiCall
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

fun interface CrudSchoolRepository{
  suspend fun executeRegisterOneSchool(
      request : CredentialsRegisterSchool
  ): ResultService<List<String?>?, FailureService>
}

class CrudSchoolRepositoryImp(
    private val registerOneSchoolApiCall: RegisterOneSchoolApiCall
) : CrudSchoolRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegisterOneSchool(
        request : CredentialsRegisterSchool
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerOneSchoolApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}