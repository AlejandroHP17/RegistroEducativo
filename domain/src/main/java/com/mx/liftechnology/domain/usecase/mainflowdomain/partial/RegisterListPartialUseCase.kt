package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestPartials
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterPartial
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.partial.RegisterListPartialRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

/**
 * Use case for registering a list of partials.
 *
 * @property registerListPartialRepository The repository for registering a list of partials.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListPartialUseCase(
    private val registerListPartialRepository: RegisterListPartialRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Executes the process of registering a list of partials.
     *
     * @param periodNumber The number of periods.
     * @param adapterPeriods The list of date periods to register.
     * @return A [ModelState] indicating the result of the registration.
     */
    suspend operator fun invoke(
        periodNumber: Int?,
        adapterPeriods: List<ModelDatePeriodDomain>
    ): ModelState<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val listAdapter: MutableList<RequestPartials> = mutableListOf()
        adapterPeriods.forEachIndexed { index,  data ->
            val part = data.date.valueText.split("/")
            listAdapter.add(
                RequestPartials(
                    description = (index + 1).toString(),
                    startDate = part.getOrNull(0)?.trim() ?: "",
                    endDate = part.getOrNull(1)?.trim() ?: "",
                )
            )
        }

        val request = RequestRegisterPartial(
            numberPartials = periodNumber,
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            userId = userId,
            teacherId = roleId,
            listPartials = listAdapter
        )

        return runCatching { registerListPartialRepository.executeRegisterListPartial(request) }.fold(
            onSuccess = { result ->
                when(result){
                    is ResultSuccess -> {
                        result.data?.let {
                            if(it.isNotEmpty()) SuccessState(result.data)
                            else ErrorState(ModelCodeError.ERROR_CRITICAL)
                        }?:ErrorState(ModelCodeError.ERROR_CRITICAL)
                    }
                    is ResultError -> { handleResponse(result.error)}
                }
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    /**
     * Handles error responses from the partials repository.
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
