package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.network.callapi.CredentialGetListStudent
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainflowdata.student.CrudStudentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentRegisterAssignmentDomain
import com.mx.liftechnology.domain.model.student.toModelStudentRegisterAssignmentList

class GetListStudentAssignmentUseCase (
    private val crudStudentRepository: CrudStudentRepository,
    private val preference: PreferenceUseCase
){
    suspend operator fun invoke(): ModelState<List<ModelStudentRegisterAssignmentDomain>?, String> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg =
            preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = CredentialGetListStudent(
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return runCatching { crudStudentRepository.executeGetListStudent(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        if (result.data.isNullOrEmpty()) ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
                        else SuccessState(result.data?.toModelStudentRegisterAssignmentList())
                    }

                    is ResultError -> {
                        handleResponseAssignment(result.error)
                    }
                }
            },
            onFailure = {ErrorState(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    private fun handleResponseAssignment(error: FailureService): ModelState<List<ModelStudentRegisterAssignmentDomain>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}