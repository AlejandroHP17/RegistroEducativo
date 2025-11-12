package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.apiCall.formativeField.GetWorkTypeApiCall
import com.mx.liftechnology.data.mapper.FormativeFieldDataToDomainMapper.mapperToModelWorkTypeByFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeByFormativeField
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface GetWorkTypeByFormativeFieldsRepository {
    suspend fun executeGetWorkTyperByFormativeFields(formativeFieldId: Int): ModelResult<ModelWorkTypeByFormativeField, NetworkError>
}
class GetWorkTypeByFormativeFieldsRepositoryImpl(
    private val getWorkTypeApiCall: GetWorkTypeApiCall
): GetWorkTypeByFormativeFieldsRepository{
    override suspend fun executeGetWorkTyperByFormativeFields(formativeFieldId: Int): ModelResult<ModelWorkTypeByFormativeField, NetworkError> {
        return try {
            val response = getWorkTypeApiCall.callApi(formativeFieldId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelWorkTypeByFormativeField())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}