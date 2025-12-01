package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldDataToDomainMapper.mapperToModelListWotyFofi
import com.mx.liftechnology.data.model.formativeField.ModelWotyFofiData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface GetListWotyFofiRepository {
    suspend fun executeGetListWotyFofi(schoolCycleId: Int): ModelResult<ModelWotyFofiData, NetworkModelError>
}

class GetListWotyFofiRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
):GetListWotyFofiRepository{
    override suspend fun executeGetListWotyFofi(schoolCycleId: Int): ModelResult<ModelWotyFofiData, NetworkModelError> {
        return try{
            val response = formativeFieldApi.getListWotyFofi(schoolCycleId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelListWotyFofi())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        }catch (e:Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }

}