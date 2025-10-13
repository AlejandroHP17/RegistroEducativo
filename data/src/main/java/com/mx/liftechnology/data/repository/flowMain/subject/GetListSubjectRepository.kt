package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListSubject
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListSubject
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the subject list repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListSubjectRepository{
    /**
     * Executes the request to get the list of subjects.
     *
     * @param request The request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun executeGetListSubject(request: RequestGetListSubject)
    : ResultService<List<ResponseGetListSubject?>?, FailureService>
}

/**
 * Implementation of [GetListSubjectRepository].
 *
 * @property getListSubjectApiCall The API call for getting the subject list.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectRepositoryImp(
    private val getListSubjectApiCall : GetListSubjectApiCall
) : GetListSubjectRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListSubject(
        request: RequestGetListSubject
    ) : ResultService<List<ResponseGetListSubject?>?, FailureService> {
        return try {
            val response = getListSubjectApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}