package com.mx.liftechnology.data.repository.flowMain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterListPartialApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterPartial
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the partial list registration repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListPartialRepository{
  /**
   * Executes the request to register a list of partials.
   *
   * @param request The request data.
   * @return A [ResultService] indicating the result of the operation.
   */
  suspend fun executeRegisterListPartial(request : RequestRegisterPartial
  ): ResultService<List<String?>?, FailureService>
}

/**
 * Implementation of [RegisterListPartialRepository].
 *
 * @property registerListPartialApiCall The API call for registering a list of partials.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListPartialRepositoryImp(
    private val registerListPartialApiCall: RegisterListPartialApiCall,
) : RegisterListPartialRepository {

    /**
     * {@inheritDoc}
     */
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