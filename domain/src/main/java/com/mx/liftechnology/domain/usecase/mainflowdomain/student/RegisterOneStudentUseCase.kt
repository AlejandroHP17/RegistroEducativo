package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.core.network.callapi.CredentialsRegisterStudent
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

fun interface RegisterOneStudentUseCase {
    suspend fun registerOneStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ): ModelState<List<String?>?, String>?
}

class RegisterOneStudentUseCaseImp(
    private val crudStudentRepository: CrudStudentRepository,
    private val preference: PreferenceUseCase
): RegisterOneStudentUseCase {

    /** Validate Email
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun registerOneStudent(
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

        val request = CredentialsRegisterStudent(
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
        return when (val result =  crudStudentRepository.executeRegisterOneStudent(request)) {
            is ResultSuccess -> {
                SuccessState(result.data)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }


    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * if not return the correct error
     * @return ModelState
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

