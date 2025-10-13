package com.mx.liftechnology.data.repository.flowMain.subject.evaluationtype

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListEvaluationTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListEvaluationType
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the evaluation type list repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListEvaluationTypeRepository {
    /**
     * Executes the request to get the list of evaluation types.
     *
     * @param request The request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun executeGetListEvaluationType( request: RequestGetListEvaluationType) : ResultService<List<String>?, FailureService>
}

/**
 * Implementation of [GetListEvaluationTypeRepository].
 *
 * @property getListEvaluationTypeApiCall The API call for getting the evaluation type list.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListEvaluationTypeRepositoryImp (
    private var getListEvaluationTypeApiCall : GetListEvaluationTypeApiCall
): GetListEvaluationTypeRepository{
    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListEvaluationType(request: RequestGetListEvaluationType): ResultService<List<String>?, FailureService> {
        return try {
            val response = getListEvaluationTypeApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}