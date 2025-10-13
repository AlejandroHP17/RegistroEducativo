package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.evaluationType

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListEvaluationType
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.evaluationtype.GetListEvaluationTypeRepository
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
 * Interface for getting the list of evaluation types.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListEvaluationTypeUseCase {
    /**
     * Executes the process of getting the list of evaluation types.
     *
     * @return A [ModelState] containing the list of evaluation types or an error.
     */
    suspend fun getListEvaluationType(): ModelState<List<String>?, String>?
}

/**
 * Implementation of [GetListEvaluationTypeUseCase].
 *
 * @property getListEvaluationTypeRepository The repository for fetching evaluation types.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListEvaluationTypeUseCaseImp (
    private val getListEvaluationTypeRepository : GetListEvaluationTypeRepository,
    private val preference: PreferenceUseCase
) : GetListEvaluationTypeUseCase {

    /**
     * {@inheritDoc}
     */
    override suspend fun getListEvaluationType(): ModelState<List<String>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestGetListEvaluationType(
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return when (val result =  getListEvaluationTypeRepository.executeGetListEvaluationType(request)) {
            is ResultSuccess -> {
                SuccessState(result.data)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /**
     * Handles error responses from the evaluation type repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
     */
    private fun handleResponse(error: FailureService): ModelState<List<String>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }



}