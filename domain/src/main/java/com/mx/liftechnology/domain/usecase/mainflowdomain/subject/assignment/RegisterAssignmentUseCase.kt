package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterJobStudent
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestStudentJobs
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseStudentJobs
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.RegisterAssignmentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

/**
 * Use case for registering an assignment.
 *
 * @property registerAssignmentRepository The repository for assignment registration.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterAssignmentUseCase (
    private val registerAssignmentRepository: RegisterAssignmentRepository,
    private val preference: PreferenceUseCase
){

    /**
     * Executes the assignment registration process.
     *
     * @param nameJob The name of the job.
     * @param typeJob The type of the job.
     * @param date The date of the assignment.
     * @param studentListUI The list of students and their job-related data.
     * @return A [ModelState] indicating the result of the registration.
     */
    suspend operator fun invoke(nameJob: String, typeJob: Int, date: String, studentListUI:  List<RequestStudentJobs>): ModelState<List<ResponseStudentJobs?>?, String> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)
        val partialCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP)

        val request = RequestRegisterJobStudent(
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

        return runCatching { registerAssignmentRepository.RegisterAssignment(request) }.fold(
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

    /**
     * Handles error responses from the assignment repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
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