package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestPercent
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSubject
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.RegisterSubjectRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods

/**
 * Use case for registering a single subject.
 *
 * @property registerSubjectRepository The repository for subject registration.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterOneSubjectUseCase(
    private val registerSubjectRepository: RegisterSubjectRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Executes the subject registration process.
     *
     * @param updatedList The list of work methods and their percentages.
     * @param name The name of the subject.
     * @return A [ModelState] indicating the result of the registration.
     */
    suspend operator fun invoke(
        updatedList: MutableList<ModelSpinnersWorkMethods>?,
        name: String?
    ): ModelState<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val listAdapter: MutableList<RequestPercent> = mutableListOf()
        updatedList?.forEach { data ->
            listAdapter.add(
                RequestPercent(
                    jobId = data.assessmentTypeId,
                    percent = data.percent.valueText.toInt(),
                    assessmentType = data.name.valueText
                )
            )
        }

        val request = RequestRegisterSubject(
            subject = name,
            options = updatedList?.size,
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            userId = userId,
            teacherId = roleId,
            percents = listAdapter
        )

        return runCatching {registerSubjectRepository.executeRegisterOneSubject(request)}.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        SuccessState(result.data)
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
     * Handles error responses from the subject repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
     */
    private fun handleResponse(error: FailureService): ModelState<List<String?>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}
