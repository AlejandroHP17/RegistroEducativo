package com.mx.liftechnology.data.repository.flowMain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.GetListStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListStudent
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetStudent
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the student list repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetStudentRepository{
    /**
     * Executes the request to get the list of students.
     *
     * @param request The request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun executeGetListStudent(request: RequestGetListStudent)
    : ResultService<List<ResponseGetStudent?>?, FailureService>
}

/**
 * Implementation of [GetStudentRepository].
 *
 * @property getListStudentApiCall The API call for getting the student list.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetStudentRepositoryImp(
    private val getListStudentApiCall : GetListStudentApiCall
) : GetStudentRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListStudent(
        request: RequestGetListStudent
    ) : ResultService<List<ResponseGetStudent?>?, FailureService> {
        return try {
            val response = getListStudentApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e:Exception){
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}