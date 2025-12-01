package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.mapper.StudentMapper.toData
import com.mx.liftechnology.data.model.student.ModelEvaluationsStudent
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

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
    ): ModelResult<List<ModelEvaluationsStudent>, NetworkModelError>
}
class GetListEvaluationsStudentRepositoryImpl(
    private val studentApi: StudentApi
):GetListEvaluationsStudentRepository {
    override suspend fun getListEvaluations(
        schoolCycleId: Int,
        partialId: Int,
        formativeFieldId: Int,
        workTypeId: Int,
        studentId: Int,
        workDate: String?,
        workDateFrom : String?,
        workDateTo: String?
    ): ModelResult<List <ModelEvaluationsStudent>, NetworkModelError> {
        return studentApi.getListEvaluations(
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