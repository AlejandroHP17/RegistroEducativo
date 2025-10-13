package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterStudent
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.student.RegisterStudentRepository
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
 * Use case for registering a single student.
 *
 * @property crudStudentRepository The repository for student CRUD operations.
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterOneStudentUseCase(
    private val crudStudentRepository: RegisterStudentRepository,
    private val preference: PreferenceUseCase
) {

    /**
     * Executes the student registration process.
     *
     * @param name The student's name.
     * @param lastName The student's last name.
     * @param secondLastName The student's second last name.
     * @param curp The student's CURP.
     * @param birthday The student's birthday.
     * @param phoneNumber The student's phone number.
     * @return A [ModelState] indicating the result of the registration.
     */
    suspend operator fun invoke(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ): ModelState<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = RequestRegisterStudent(
            name = name,
            lastName = lastName,
            secondLastName = secondLastName,
            curp = curp,
            birthday = birthday,
            phoneNumber = phoneNumber,
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return runCatching { crudStudentRepository.executeRegisterOneStudent(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        SuccessState(result.data)
                    }

                    is ResultError -> {
                        handleResponse(result.error)
                    }
                }
                        },
            onFailure = {ErrorState(ModelCodeError.ERROR_UNKNOWN)}
        )
    }


    /**
     * Handles error responses from the student registration repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
     */
    private fun handleResponse(error: FailureService): ModelState<List<String?>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}
