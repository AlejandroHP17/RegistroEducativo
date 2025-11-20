package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.core.network.apiCall.evaluation.RequestListGrades
import com.mx.liftechnology.core.network.apiCall.evaluation.RequestWorkTypeEvaluations
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.evaluation.RegisterWorkTypeEvaluationsRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

class RegisterWorkTypeEvaluationsUseCase(
    private val preference : PreferenceUseCase,
    private val registerWorkTypeEvaluationsRepository: RegisterWorkTypeEvaluationsRepository
) {
    suspend operator fun invoke(workTypeId: Int?, nameWork: String?, workDate: String?, studentListUI: List<RequestListGrades>):  ModelResult<Boolean, Error>{
        val formativeFieldId = preference.getPreferenceInt(ModelPreference.ID_FORMATIVE_FIELD)
        val partialId= preference.getPreferenceInt(ModelPreference.ID_PARTIAL)
        val cycleSchoolId= preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if(formativeFieldId == null || partialId == null || cycleSchoolId == null || workDate == null || nameWork == null|| workTypeId == null) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        val request= RequestWorkTypeEvaluations(
            formativeFieldId = formativeFieldId,
            partialId = partialId,
            workTypeId = workTypeId,
            nameWork = nameWork,
            workDate = workDate,
            schoolCycleId = cycleSchoolId,
            grades = studentListUI
        )
        return runCatching { registerWorkTypeEvaluationsRepository.executeRegisterWorkTyperEvaluations(request) }.fold(
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
            onFailure = { ErrorResult(NetworkError.UNKNOWN) }
        )
    }
}