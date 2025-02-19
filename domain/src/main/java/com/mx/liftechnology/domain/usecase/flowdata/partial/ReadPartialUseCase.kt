package com.mx.liftechnology.domain.usecase.flowdata.partial

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ErrorUnauthorizedState
import com.mx.liftechnology.core.model.modelBase.ErrorUserState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.network.callapi.CredentialsGetPartial
import com.mx.liftechnology.core.network.util.FailureService
import com.mx.liftechnology.core.network.util.ResultError
import com.mx.liftechnology.core.network.util.ResultSuccess
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.registerFlow.CrudPartialRepository
import com.mx.liftechnology.domain.model.ModelDatePeriod

fun interface ReadPartialUseCase {
    suspend fun readPartials(): ModelState<MutableList<ModelDatePeriod>?, String>?

}
class ReadPartialUseCaseImp (
    private val crudPartialRepository: CrudPartialRepository,
    private val preference: PreferenceUseCase
) : ReadPartialUseCase {
    override suspend fun readPartials(): ModelState<MutableList<ModelDatePeriod>?, String>? {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = CredentialsGetPartial(
            profesorescuelaciclogrupo_id = profSchoolCycleGroupId,
            user_id = userId,
            profesor_id = roleId
        )

        return when (val result =  crudPartialRepository.executeGetPartial(request)) {
            is ResultSuccess -> {
                val result = result.data?.mapIndexed { index, item ->
                    ModelDatePeriod(
                        position = index,
                        date = "${item?.fechainicio} / ${item?.fechafinal}" // Reemplaza con los campos que necesites
                    )
                } ?.toMutableList()
                if (result?.size!! > 0)
                SuccessState(result)
                else
                    ErrorState(ModelCodeError.ERROR_UNKNOWN)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    private fun handleResponse(error: FailureService): ModelState<MutableList<ModelDatePeriod>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}