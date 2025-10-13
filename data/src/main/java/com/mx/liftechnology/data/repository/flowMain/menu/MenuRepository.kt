package com.mx.liftechnology.data.repository.flowMain.menu

import com.mx.liftechnology.core.network.apiCall.flowMain.GroupApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGroup
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGroupTeacher
import com.mx.liftechnology.data.util.ExceptionHandler
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultService
import com.mx.liftechnology.data.util.ResultSuccess
import retrofit2.HttpException

/**
 * Interface for the menu repository.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface MenuRepository{
    /**
     * Executes the request to get the group list for the menu.
     *
     * @param request The request data.
     * @return A [ResultService] indicating the result of the operation.
     */
    suspend fun executeGetGroup(
        request: RequestGroup
    ): ResultService<List<ResponseGroupTeacher?>?, FailureService>
}

/**
 * Implementation of [MenuRepository].
 *
 * @property groupApiCall The API call for getting the group list.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuRepositoryImp(
    private val groupApiCall: GroupApiCall
): MenuRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetGroup(request: RequestGroup): ResultService<List<ResponseGroupTeacher?>?, FailureService> {
        return try {
            val response = groupApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}