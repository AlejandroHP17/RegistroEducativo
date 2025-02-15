package com.mx.liftechnology.domain.usecase.flowregisterdata

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorUnauthorizedState
import com.mx.liftechnology.core.model.modelBase.ErrorUserState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CredentialsRegisterSubject
import com.mx.liftechnology.core.network.callapi.Percent
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultSuccess
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.registerFlow.RegisterSubjectRepository
import com.mx.liftechnology.domain.model.ModelFormatSubject


fun interface RegisterSubjectUseCase {
    suspend fun putSubjects(
        updatedList: MutableList<ModelFormatSubject>?,
        name: String?
    ): ModelState<List<String?>?, String>?
}

class RegisterSubjectUseCaseImp(
    private val registerSubjectRepository: RegisterSubjectRepository,
    private val preference: PreferenceUseCase
): RegisterSubjectUseCase {

    /** Validate Email
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun putSubjects(
        updatedList: MutableList<ModelFormatSubject>?,
        name: String?
    ): ModelState<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val listAdapter: MutableList<Percent> = mutableListOf()
        updatedList?.forEach { data ->
            listAdapter.add(
                Percent(
                    trabajo_id = data.position,
                    porcentaje = data.percent?.toInt()
                )
            )
        }

        val request = CredentialsRegisterSubject(
            campoformativo = name,
            opciones = updatedList?.size,
            profesorescuelaciclogrupo_id = profSchoolCycleGroupId,
            user_id = userId,
            profesor_id = roleId,
            porcentajes = listAdapter
        )

        return when (val result =  registerSubjectRepository.executeRegisterSubject(request)) {
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
