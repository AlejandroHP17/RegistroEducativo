package com.mx.liftechnology.data.repository.registerFlow

import com.mx.liftechnology.core.network.callapi.CredentialsRegisterSubject
import com.mx.liftechnology.core.network.callapi.RegisterSubjectApiCall
import com.mx.liftechnology.core.network.util.ExceptionHandler
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultService
import com.mx.liftechnology.core.network.util.ResultSuccess
import retrofit2.HttpException

fun interface RegisterSubjectRepository{
  suspend fun executeRegisterSubject(
      request : CredentialsRegisterSubject
  ): ResultService<List<String?>?, FailureService>
}

class RegisterSubjectRepositoryImp(
    private val registerSubjectApiCall: RegisterSubjectApiCall
) : RegisterSubjectRepository {

    /** Execute the user register
     * @author pelkidev
     * @since 1.0.0
     */
    override suspend fun executeRegisterSubject(
        request : CredentialsRegisterSubject
    ): ResultService<List<String?>?, FailureService> {
        return try {
            val response = registerSubjectApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}