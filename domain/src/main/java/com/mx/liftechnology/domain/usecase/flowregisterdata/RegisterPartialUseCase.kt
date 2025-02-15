package com.mx.liftechnology.domain.usecase.flowregisterdata

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorUnauthorizedState
import com.mx.liftechnology.core.model.modelBase.ErrorUserState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CredentialsRegisterPartial
import com.mx.liftechnology.core.network.callapi.Partials
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultSuccess
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.registerFlow.RegisterPartialRepository
import com.mx.liftechnology.domain.model.ModelDatePeriod


fun interface RegisterPartialUseCase {
    suspend fun putPartials(
        periodNumber: Int?,
        adapterPeriods: MutableList<ModelDatePeriod>?
    ): ModelState<List<String?>?, String>?
}

class RegisterPartialUseCaseImp(
    private val registerPartialRepository: RegisterPartialRepository,
    private val preference: PreferenceUseCase
): RegisterPartialUseCase {

    /** Validate Email
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun putPartials(
        periodNumber: Int?,
        adapterPeriods: MutableList<ModelDatePeriod>?
    ): ModelState<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val listAdapter: MutableList<Partials> = mutableListOf()
        adapterPeriods?.forEach { data ->
            val part = data.date?.split("/")
            listAdapter.add(
                Partials(
                    descripcion = (data.position + 1).toString(),
                    fechainicio = part?.getOrNull(0)?.trim() ?: "",
                    fechafinal = part?.getOrNull(1)?.trim() ?: "",
                )
            )
        }

        val request = CredentialsRegisterPartial(
            numparciales = periodNumber,
            profesorescuelaciclogrupo_id = profSchoolCycleGroupId,
            user_id = userId,
            profesor_id = roleId,
            parciales = listAdapter
        )

        return when (val result =  registerPartialRepository.executeRegisterPartial(request)) {
            is ResultSuccess -> {
                SuccessState(result.data)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
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

