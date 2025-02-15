package com.mx.liftechnology.domain.usecase.flowgetdata

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorUnauthorizedState
import com.mx.liftechnology.core.model.modelBase.ErrorUserState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CredentialGetListSubject
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultSuccess
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.getFlow.GetListSubjectRepository

fun interface GetListSubjectUseCase {
    suspend fun getListSubject(): ModelState<List<String?>?, String>?
}

class GetListSubjectUseCaseImp (
    private val getListSubjectRepository : GetListSubjectRepository,
    private val preference: PreferenceUseCase
) : GetListSubjectUseCase{
    override suspend fun getListSubject(): ModelState<List<String?>?, String>? {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = CredentialGetListSubject(
            profesor_id = roleId,
            user_id = userId,
            profesorescuelaciclogrupo_id = pecg
        )

        return when (val result =  getListSubjectRepository.executeGetListSubject(request)) {
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
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

}