package com.mx.liftechnology.data.repository.mainFlow

import com.mx.liftechnology.core.network.callapi.CredentialsGroup
import com.mx.liftechnology.core.network.callapi.GroupApiCall
import com.mx.liftechnology.core.network.callapi.ResponseGroupTeacher
import com.mx.liftechnology.core.network.util.ExceptionHandler
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultService
import com.mx.liftechnology.core.network.util.ResultSuccess
import retrofit2.HttpException

fun interface MenuRepository{
    suspend fun executeGetGroup(
        request: CredentialsGroup
    ): ResultService<List<ResponseGroupTeacher?>?, FailureService>
}

/** MenuRepository - Build the element list of menu (home)
 * @author pelkidev
 * @since 1.0.0
 * @return listMenuItems contains the list of menu
 * */
class MenuRepositoryImp(
    private val groupApiCall: GroupApiCall
): MenuRepository {

    override suspend fun executeGetGroup(request: CredentialsGroup):ResultService<List<ResponseGroupTeacher?>?, FailureService> {
        return try {
            val response = groupApiCall.callApi(request)
            if (response.isSuccessful) ResultSuccess(response.body()?.data)
            else ResultError(ExceptionHandler.handleException(HttpException(response)))
        } catch (e: Exception) {
            ResultError(ExceptionHandler.handleException(e))
        }
    }
}