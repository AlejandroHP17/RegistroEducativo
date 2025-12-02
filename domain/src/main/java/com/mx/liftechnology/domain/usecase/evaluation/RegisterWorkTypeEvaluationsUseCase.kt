package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.core.network.api.RequestListGrades

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.evaluation.CardDomain
import com.mx.liftechnology.domain.repository.evaluation.RegisterWorkTypeEvaluationsRepository

class RegisterWorkTypeEvaluationsUseCase(
    private val preference : PreferenceUseCase,
    private val registerWorkTypeEvaluationsRepository: RegisterWorkTypeEvaluationsRepository
) {
    suspend operator fun invoke(workTypeId: Int?, nameWork: String?, workDate: String?, studentListUI: List<CardDomain>): ModelResult<Boolean, ModelError> {
        val formativeFieldId = preference.getIdFormativeField()
        val partialId= preference.getIdPartial()
        val cycleSchoolId= preference.getIdCycleSchool()

        if(formativeFieldId == null || partialId == null || cycleSchoolId == null || workDate == null || nameWork == null|| workTypeId == null) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        return runCatching { registerWorkTypeEvaluationsRepository.register(
            formativeFieldId = formativeFieldId,
            partialId = partialId,
            workTypeId = workTypeId,
            nameWork = nameWork.trim(),
            workDate = workDate.trim(),
            schoolCycleId = cycleSchoolId,
            grades = studentListUI.toCredentialStudent()
        ) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        SuccessResult(true)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }

    private fun List<CardDomain>.toCredentialStudent()  : List<RequestListGrades>{
        return this.map { student ->
            RequestListGrades(
                studentId = student.studentId,
                grade = student.grade,
            )
        }
    }
}