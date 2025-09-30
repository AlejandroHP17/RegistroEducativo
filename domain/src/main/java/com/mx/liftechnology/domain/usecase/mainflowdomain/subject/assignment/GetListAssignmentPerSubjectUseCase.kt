package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetPercentSubjectId
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPercentSubjectId
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetPercentSubjectRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelFormatAssignment

class GetListAssignmentPerSubjectUseCase (
    private val getPercentSubjectRepository: GetPercentSubjectRepository,
    private val preference : PreferenceUseCase
) {
    suspend operator fun invoke(): ModelState<List<ModelFormatAssignment>?, String?>{

        val teacherId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)
        val subjectSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP)

        val request = RequestGetPercentSubjectId(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId,
            subjectSchoolCycleGroupId = subjectSchoolCycleGroupId
        )

        return runCatching { getPercentSubjectRepository.executeGetPercentSubject(request) }.fold(
            onSuccess = { result->
                when(result) {
                    is ResultSuccess -> {
                        val data = result.data.toModelUseCase()
                        if(data.isNotEmpty())SuccessState(data)
                        else ErrorState(ModelCodeError.ERROR_UNKNOWN)
                    }
                    is ResultError -> handleResponse(result.error)
                }
            },
            onFailure = {ErrorState(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    private fun List<ResponseGetPercentSubjectId>?.toModelUseCase() : List<ModelFormatAssignment> {
        return this?.map {
            ModelFormatAssignment(
                id = it.id,
                percent= it.percent,
                subjectSchoolCycleGroupId = it.subjectSchoolCycleGroupId,
                description	= it.description.stringToModelStateOutFieldText(),
                teacherSchoolCycleGroupId= it.teacherSchoolCycleGroupId,
                assignmentId= it.assignmentId,
                assignmentName= it.assignmentName.stringToModelStateOutFieldText()
            )
        }?: emptyList()
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * @param error in order to validate the code and if is success, return the body
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(error: FailureService): ModelState<List<ModelFormatAssignment>?, String?> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_INFO)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}