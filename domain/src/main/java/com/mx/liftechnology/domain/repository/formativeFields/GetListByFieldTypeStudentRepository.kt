package com.mx.liftechnology.domain.repository.formativeFields

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.formativeFields.ModelByFieldTypeStudentData

fun interface GetListByFieldTypeStudentRepository {
    suspend fun getByFieldType(
        formativeFieldId : Int,
        workTypeId : Int,
        workName : String?,
        workDate : String?
    ): ModelResult<ModelByFieldTypeStudentData, NetworkModelError>
}