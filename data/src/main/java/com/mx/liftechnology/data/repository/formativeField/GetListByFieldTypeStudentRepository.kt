package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.data.model.formativeField.ModelByFieldTypeStudentData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

fun interface GetListByFieldTypeStudentRepository {
    suspend fun getByFieldType(
        formativeFieldId : Int,
        workTypeId : Int,
        workName : String?,
        workDate : String?
    ): ModelResult<ModelByFieldTypeStudentData, NetworkModelError>
}

class GetListByFieldTypeStudentRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
):GetListByFieldTypeStudentRepository{
    override suspend fun getByFieldType(
        formativeFieldId : Int,
        workTypeId : Int,
        workName : String?,
        workDate : String?
    ): ModelResult<ModelByFieldTypeStudentData, NetworkModelError> {
        return formativeFieldApi.getListByFieldTypeStudent(
            formativeFieldId = formativeFieldId,
            workTypeId = workTypeId,
            workName = workName,
            workDate = workDate
        ).executeOrError { it.toData() }
    }
}