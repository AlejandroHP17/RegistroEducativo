package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.core.network.callapi.CredentialsGetPartial
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainflowdata.partial.CrudPartialRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.menu.ListPartialToConvertModelDialogGroupPartialDomains
import com.mx.liftechnology.domain.model.menu.ModelDialogGroupPartialDomain

fun interface GetListPartialMenuUseCase {
    suspend fun getListPartial(): ModelState<List<ModelDialogGroupPartialDomain>?, String>?
}
class GetListPartialMenuUseCaseImp (
    private val crudPartialRepository: CrudPartialRepository,
    private val preference: PreferenceUseCase
) : GetListPartialMenuUseCase {
    override suspend fun getListPartial(): ModelState<List<ModelDialogGroupPartialDomain>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = CredentialsGetPartial(
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            userId = userId,
            teacherId = roleId
        )

        return when (val result =  crudPartialRepository.executeGetListPartial(request)) {
            is ResultSuccess -> {
                val convertedResult = result.data.ListPartialToConvertModelDialogGroupPartialDomains
                if (convertedResult.isNotEmpty()) {
                    SuccessState(convertedResult)
                }
                else ErrorState(ModelCodeError.ERROR_UNKNOWN)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    private fun handleResponse(error: FailureService): ModelState<List<ModelDialogGroupPartialDomain>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}