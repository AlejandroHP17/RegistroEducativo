package com.mx.liftechnology.data.repositoryImpl.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.domain.model.formativeFields.ByFieldTypeStudentDomain
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError
import com.mx.liftechnology.domain.repository.formativeFields.GetListByFieldTypeStudentRepository


class GetListByFieldTypeStudentRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
): GetListByFieldTypeStudentRepository {
    override suspend fun getByFieldType(
        formativeFieldId : Int,
        workTypeId : Int,
        workName : String?,
        workDate : String?
    ): ModelResult<ByFieldTypeStudentDomain, NetworkModelError> {
        return formativeFieldApi.getListByFieldTypeStudent(
            formativeFieldId = formativeFieldId,
            workTypeId = workTypeId,
            workName = workName,
            workDate = workDate
        ).executeOrError { it.toData() }
    }
}