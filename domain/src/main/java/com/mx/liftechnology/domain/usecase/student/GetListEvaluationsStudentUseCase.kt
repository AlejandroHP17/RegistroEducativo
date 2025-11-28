package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.student.ModelEvaluationsStudent
import com.mx.liftechnology.data.repository.student.GetListEvaluationsStudentRepository
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult

class GetListEvaluationsStudentUseCase(
    private val preference: PreferenceUseCase,
    private val getListEvaluationsStudentRepository: GetListEvaluationsStudentRepository,
) {
    suspend operator fun invoke(workTypeId: Int?, studentId: Int?, formativeFieldId : Int?): ModelResult<List<ModelEvaluationsStudent>, ModelError> {
        val schoolCycleId = preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)
        val partialId = preference.getPreferenceInt(ModelPreference.ID_PARTIAL)

        if (schoolCycleId == null || partialId == null || studentId == null || workTypeId== null || formativeFieldId == null) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        return runCatching {
            getListEvaluationsStudentRepository.executeGetListEvaluationsStudent(
                schoolCycleId = schoolCycleId,
                partialId = partialId,
                formativeFieldId = formativeFieldId,
                workTypeId = workTypeId,
                studentId = studentId
            )
        }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        SuccessResult(result.data)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )

    }
}