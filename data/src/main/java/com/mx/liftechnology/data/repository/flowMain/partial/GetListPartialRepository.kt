package com.mx.liftechnology.data.repository.flowMain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListPartialApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetPartial
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPartial
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the partial list repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListPartialRepository{
  /**
   * Executes the request to get the list of partials.
   *
   * @param request The request data.
   * @return A [ResultService] indicating the result of the operation.
   */
  suspend fun executeGetListPartial(
      request : RequestGetPartial
  ): ResultService<List<ResponseGetPartial?>?, FailureService>
}

/**
 * Implementation of [GetListPartialRepository].
 *
 * @property getListPartialApiCall The API call for getting the partial list.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialRepositoryImp(
    private val getListPartialApiCall: GetListPartialApiCall
) : GetListPartialRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListPartial(
        request : RequestGetPartial
    ): ResultService<List<ResponseGetPartial?>?, FailureService> {
        return try {
            val response = getListPartialApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}