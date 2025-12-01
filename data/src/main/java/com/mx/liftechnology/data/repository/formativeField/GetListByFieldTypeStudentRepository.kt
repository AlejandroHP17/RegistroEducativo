package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.mapperToModelByFieldTypeStudentData
import com.mx.liftechnology.data.model.formativeField.ModelByFieldTypeStudentData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface GetListByFieldTypeStudentRepository {
    suspend fun executeGetListByFieldTypeStudent(
        formativeFieldId : Int,
        workTypeId : Int,
        workName : String?,
        workDate : String?
    ): ModelResult<ModelByFieldTypeStudentData, NetworkModelError>
}

class GetListByFieldTypeStudentRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
):GetListByFieldTypeStudentRepository{
    override suspend fun executeGetListByFieldTypeStudent(
        formativeFieldId : Int,
        workTypeId : Int,
        workName : String?,
        workDate : String?
    ): ModelResult<ModelByFieldTypeStudentData, NetworkModelError> {
        return try{
            val response = formativeFieldApi.getListByFieldTypeStudent(
                formativeFieldId = formativeFieldId,
                workTypeId = workTypeId,
                workName = workName,
                workDate = workDate
            )
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelByFieldTypeStudentData())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        }catch (e:Exception){
            ErrorResult(NetworkException.handleException(e))
        }
    }
}