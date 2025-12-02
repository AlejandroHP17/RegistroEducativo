package com.mx.liftechnology.data.repositoryImpl.student

import com.mx.liftechnology.core.network.api.WorkTypeApi
import com.mx.liftechnology.data.mapper.StudentMapper.toData
import com.mx.liftechnology.domain.model.student.EvaluationsStudent
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError
import com.mx.liftechnology.domain.repository.student.GetListEvaluationsStudentRepository


class GetListEvaluationsStudentRepositoryImpl(
    private val workTypeApi: WorkTypeApi
): GetListEvaluationsStudentRepository {
    override suspend fun getListEvaluations(
        schoolCycleId: Int,
        partialId: Int,
        formativeFieldId: Int,
        workTypeId: Int,
        studentId: Int,
        workDate: String?,
        workDateFrom : String?,
        workDateTo: String?
    ): ModelResult<List <EvaluationsStudent>, NetworkModelError> {
        return workTypeApi.getListEvaluations(
            formativeFieldId = formativeFieldId,
            partialId = partialId,
            workTypeId = workTypeId,
            schoolCycleId = schoolCycleId,
            studentId = studentId,
            workDate = workDate,
            workDateFrom = workDateFrom,
            workDateTo = workDateTo,
        ).executeOrError { it.toData() }
    }
}