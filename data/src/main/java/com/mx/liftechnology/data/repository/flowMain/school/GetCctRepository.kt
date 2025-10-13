package com.mx.liftechnology.data.repository.flowMain.school

import com.mx.liftechnology.core.network.apiCall.flowMain.GetCctApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the CCT repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetCctRepository{
  /**
   * Executes the request to get school data by CCT.
   *
   * @param cct The CCT of the school.
   * @return A [ResultService] indicating the result of the operation.
   */
  suspend fun executeGetCct(cct:String): ResultService<ResponseCctSchool?, FailureService>
}

/**
 * Implementation of [GetCctRepository].
 *
 * @property cctApiCall The API call for getting school data.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetCctRepositoryImp(
    private val cctApiCall: GetCctApiCall
) : GetCctRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetCct(cct:String): ResultService<ResponseCctSchool?, FailureService> {
        return try {
            val response = cctApiCall.callApi(cct)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}