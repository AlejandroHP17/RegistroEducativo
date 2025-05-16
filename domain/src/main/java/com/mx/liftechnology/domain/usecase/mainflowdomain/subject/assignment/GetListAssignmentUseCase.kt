package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.callapi.CredentialsGetListAssignment
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainflowdata.subject.assignment.CrudAssignmentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

fun interface GetListAssignmentUseCase {
    suspend fun getListAssignment () :ModelState<List<String>?, String?>
}

class GetListAssignmentUseCaseImp(
    private val crudAssignmentRepository: CrudAssignmentRepository,
    private val preference : PreferenceUseCase
): GetListAssignmentUseCase {
    override suspend fun getListAssignment():ModelState<List<String>?, String?> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = CredentialsGetListAssignment(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId
        )

        return when(val result =  crudAssignmentRepository.executeGetListAssignment(request)){
            is ResultSuccess -> SuccessState(result.data)
            is ResultError -> handleResponse(result.error)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * @param error in order to validate the code and if is success, return the body
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(error: FailureService): ModelState<List<String>?, String?> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_INFO)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}