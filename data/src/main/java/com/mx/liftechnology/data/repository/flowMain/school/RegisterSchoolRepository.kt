package com.mx.liftechnology.data.repository.flowMain.school

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSchoolApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSchool
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the school registration repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterSchoolRepository{
  /**
   * Executes the school registration request.
   *
   * @param request The school registration request data.
   * @return A [ResultService] indicating the result of the operation.
   */
  suspend fun executeRegisterOneSchool(
      request : RequestRegisterSchool
  ): ResultService<List<String?>?, FailureService>
}

/**
 * Implementation of [RegisterSchoolRepository].
 *
 * @property registerSchoolApiCall The API call for school registration.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSchoolRepositoryImp(
    private val registerSchoolApiCall: RegisterSchoolApiCall
) : RegisterSchoolRepository {

    /**
     * {@inheritDoc}
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