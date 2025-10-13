package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListAssignment
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetListAssignmentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

/**
 * Interface for getting the list of assignments.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListAssignmentUseCase {
    /**
     * Executes the process of getting the list of assignments.
     *
     * @return A [ModelState] containing the list of assignment names or an error.
     */
    suspend fun getListAssignment () :ModelState<List<String>?, String?>
}

/**
 * Implementation of [GetListAssignmentUseCase].
 *
 * @property getListAssignmentRepository The repository for fetching the assignment list.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssignmentUseCaseImp(
    private val getListAssignmentRepository: GetListAssignmentRepository,
    private val preference : PreferenceUseCase
): GetListAssignmentUseCase {
    /**
     * {@inheritDoc}
     */
    override suspend fun getListAssignment():ModelState<List<String>?, String?> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestGetListAssignment(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId
        )

        return when(val result =  getListAssignmentRepository.executeGetListAssignment(request)){
            is ResultSuccess -> SuccessState(result.data)
            is ResultError -> handleResponse(result.error)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /**
     * Handles error responses from the assignment repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
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