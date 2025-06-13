package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.network.callapi.CredentialsRegisterPartial
import com.mx.liftechnology.core.network.callapi.Partials
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainflowdata.partial.CrudPartialRepository
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


class RegisterListPartialUseCase(
    private val crudPartialRepository: CrudPartialRepository,
    private val preference: PreferenceUseCase
) {

    suspend operator fun invoke(
        periodNumber: Int?,
        adapterPeriods: List<ModelDatePeriodDomain>
    ): ModelState<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val listAdapter: MutableList<Partials> = mutableListOf()
        adapterPeriods.forEachIndexed { index,  data ->
            val part = data.date.valueText.split("/")
            listAdapter.add(
                Partials(
                    description = (index + 1).toString(),
                    startDate = part.getOrNull(0)?.trim() ?: "",
                    endDate = part.getOrNull(1)?.trim() ?: "",
                )
            )
        }

        val request = CredentialsRegisterPartial(
            numberPartials = periodNumber,
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            userId = userId,
            teacherId = roleId,
            listPartials = listAdapter
        )

        return runCatching { crudPartialRepository.executeRegisterListPartial(request) }.fold(
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

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * if not return the correct error
     * @return ModelState
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

