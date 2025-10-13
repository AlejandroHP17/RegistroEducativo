package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListSubject
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.GetListSubjectRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.model.subject.toModelSubjectList

/**
 * Use case for getting the list of subjects.
 *
 * @property getListSubjectRepository The repository for fetching the subject list.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectUseCase (
    private val getListSubjectRepository : GetListSubjectRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Executes the process of getting the list of subjects.
     *
     * @return A [ModelState] containing the list of subjects or an error.
     */
    suspend operator fun invoke(): ModelState<List<ModelFormatSubjectDomain>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestGetListSubject(
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return runCatching { getListSubjectRepository.executeGetListSubject(request) }.fold(
            onSuccess = { result ->
                when(result){
                    is ResultSuccess -> {
                        if (result.data.isNullOrEmpty()) ErrorUserState(ModelCodeError.ERROR_DATA)
                        else SuccessState(result.data?.toModelSubjectList())
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
    private fun handleResponse(error: FailureService): ModelState<List<ModelFormatSubjectDomain>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

}