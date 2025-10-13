package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assessment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssessmentType
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assessment.GetAssessmentTypeRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

/**
 * Use case for getting the list of assessment types.
 *
 * @property getAssessmentTypeRepository The repository for fetching assessment types.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssessmentTypeUseCase(
    private val getAssessmentTypeRepository: GetAssessmentTypeRepository,
    private val preference : PreferenceUseCase
) {
    /**
     * Executes the process of getting the list of assessment types.
     *
     * @return A [ModelState] containing the list of assessment types or an error.
     */
    suspend operator fun invoke():ModelState<List<ResponseGetListAssessmentType?>, String?> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestGetListAssessmentType(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId
        )

        return runCatching { getAssessmentTypeRepository.executeGetListAssessment(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        result.data?.let {
                            SuccessState(result.data!!)
                        }?: ErrorState(ModelCodeError.ERROR_UNKNOWN)
                    }

                    is ResultError -> {
                        handleResponse(result.error)
                    }
                }
            },
            onFailure = {ErrorState(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    /**
     * Handles error responses from the assessment type repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
     */
    private fun handleResponse(error: FailureService): ModelState<List<ResponseGetListAssessmentType?>, String?> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_INFO)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}