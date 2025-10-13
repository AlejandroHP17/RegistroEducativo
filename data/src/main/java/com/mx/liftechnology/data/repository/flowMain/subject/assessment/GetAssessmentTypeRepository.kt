package com.mx.liftechnology.data.repository.flowMain.subject.assessment

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssessmentTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssessmentType
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the assessment type repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetAssessmentTypeRepository{
    /**
     * Executes the request to get the list of assessment types.
     *
     * @param request The request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun executeGetListAssessment(request : RequestGetListAssessmentType)
            : ResultService<List<ResponseGetListAssessmentType?>?, FailureService>
}

/**
 * Implementation of [GetAssessmentTypeRepository].
 *
 * @property getListAssessmentTypeApiCall The API call for getting the assessment type list.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetAssessmentTypeRepositoryImp(
    private val getListAssessmentTypeApiCall: GetListAssessmentTypeApiCall
) : GetAssessmentTypeRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListAssessment(
        request : RequestGetListAssessmentType
    ): ResultService<List<ResponseGetListAssessmentType?>?, FailureService> {
        return try {
            val response = getListAssessmentTypeApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }

}