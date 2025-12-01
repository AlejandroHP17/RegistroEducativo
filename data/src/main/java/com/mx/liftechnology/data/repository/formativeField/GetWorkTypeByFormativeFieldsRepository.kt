package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.mapperToModelWorkTypeByFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeByFormativeField
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface GetWorkTypeByFormativeFieldsRepository {
    suspend fun executeGetWorkTyperByFormativeFields(formativeFieldId: Int): ModelResult<ModelWorkTypeByFormativeField, NetworkModelError>
}
class GetWorkTypeByFormativeFieldsRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
): GetWorkTypeByFormativeFieldsRepository{
    override suspend fun executeGetWorkTyperByFormativeFields(formativeFieldId: Int): ModelResult<ModelWorkTypeByFormativeField, NetworkModelError> {
        return try {
            val response = formativeFieldApi.getWorkTypeByFormativeField(formativeFieldId)
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