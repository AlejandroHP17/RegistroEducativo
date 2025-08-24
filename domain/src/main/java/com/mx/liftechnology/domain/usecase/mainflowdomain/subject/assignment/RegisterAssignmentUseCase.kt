package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.callapi.CredentialStudentJobs
import com.mx.liftechnology.core.network.callapi.CredentialsRegisterOneJobStudent
import com.mx.liftechnology.core.network.callapi.ResponseStudentJobs
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainflowdata.subject.RegisterAssignmentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

class RegisterAssignmentUseCase (
    private val registerAssignmentRepository: RegisterAssignmentRepository,
    private val preference: PreferenceUseCase
){

    suspend operator fun invoke(nameJob: String, typeJob: Int, date: String, studentListUI:  List<CredentialStudentJobs>): ModelState<List<ResponseStudentJobs?>?, String> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)
        val partialCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP)

        val request = CredentialsRegisterOneJobStudent(
            description = nameJob,
            date = date,
            number = 1,
            typeJobPecgId = typeJob,
            fieldPecgPercentId = typeJob,
            userId = userId,
            teacherId = roleId,
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            partialCycleGroupId = partialCycleGroupId?:1,
            dayPartialCycleGroupId = 1,
            studentJobs = studentListUI

        )

        return runCatching { registerAssignmentRepository.executePutAssignment(request) }.fold(
            onSuccess = { result ->
                when (result){
                    is ResultSuccess -> {
                        result.data?.let {
                            SuccessState(result.data)
                        }?: ErrorState(ModelCodeError.ERROR_CRITICAL)
                    }
                    is ResultError -> {
                        handleResponse(result.error)
                    }

                }
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_CRITICAL) }
        )
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * @param error in order to validate the code and if is success, return the body
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(error: FailureService): ModelState<List<ResponseStudentJobs?>?, String> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Unauthorized -> ErrorState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_LOGIN)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}