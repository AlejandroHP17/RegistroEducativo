package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.network.callapi.CredentialGetListSubject
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainflowdata.subject.CrudSubjectRepository
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

fun interface GetListSubjectUseCase {
    suspend fun getListSubject(): ModelState<List<ModelFormatSubjectDomain>?, String>?
}

class GetListSubjectUseCaseImp (
    private val crudSubjectRepository : CrudSubjectRepository,
    private val preference: PreferenceUseCase
) : GetListSubjectUseCase {
    override suspend fun getListSubject(): ModelState<List<ModelFormatSubjectDomain>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = CredentialGetListSubject(
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return when (val result =  crudSubjectRepository.executeGetListSubject(request)) {
            is ResultSuccess -> {
                if (result.data.isNullOrEmpty()) ErrorUserState(ModelCodeError.ERROR_DATA)
                else SuccessState(result.data?.toModelSubjectList())
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