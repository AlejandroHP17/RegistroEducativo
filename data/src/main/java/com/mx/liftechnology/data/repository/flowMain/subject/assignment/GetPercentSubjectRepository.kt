package com.mx.liftechnology.data.repository.flowMain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.GetPercentSubjectIdApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetPercentSubjectId
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPercentSubjectId
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the subject percentage repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetPercentSubjectRepository{
    /**
     * Executes the request to get the percentage of a subject.
     *
     * @param request The request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun executeGetPercentSubject(request : RequestGetPercentSubjectId): ResultService<List<ResponseGetPercentSubjectId>?, FailureService>
}

/**
 * Implementation of [GetPercentSubjectRepository].
 *
 * @property getPercentSubjectIdApiCall The API call for getting the subject percentage.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetPercentSubjectRepositoryImp(private val getPercentSubjectIdApiCall: GetPercentSubjectIdApiCall
): GetPercentSubjectRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetPercentSubject(request: RequestGetPercentSubjectId) : ResultService<List<ResponseGetPercentSubjectId>?, FailureService>{
        return try{
            val response = getPercentSubjectIdApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        }catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}