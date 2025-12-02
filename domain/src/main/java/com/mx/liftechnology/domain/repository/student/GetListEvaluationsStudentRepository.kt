package com.mx.liftechnology.domain.repository.student

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain

fun interface GetListEvaluationsStudentRepository{
    suspend fun getListEvaluations(
        schoolCycleId: Int,
        partialId: Int,
        formativeFieldId: Int,
        workTypeId: Int,
        studentId: Int,
        workDate: String?,
        workDateFrom : String?,
        workDateTo: String?
    ): ModelResult<List<EvaluationsStudentDomain>, NetworkModelError>
}