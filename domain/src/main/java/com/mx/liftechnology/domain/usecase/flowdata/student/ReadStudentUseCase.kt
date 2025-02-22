package com.mx.liftechnology.domain.usecase.flowdata.student

import com.mx.liftechnology.core.network.callapi.CredentialGetListStudent
import com.mx.liftechnology.core.network.callapi.ResponseGetStudent
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.registerFlow.CrudStudentRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.ModelStudent
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

fun interface ReadStudentUseCase {
    suspend fun getListStudent(): ModelState<List<ModelStudent?>?, String>?
}

class ReadStudentUseCaseImp(
    private val crudStudentRepository: CrudStudentRepository,
    private val preference: PreferenceUseCase
) : ReadStudentUseCase {
    override suspend fun getListStudent(): ModelState<List<ModelStudent?>?, String> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val pecg =
            preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val request = CredentialGetListStudent(
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return when (val result = crudStudentRepository.executeGetListStudent(request)) {
            is ResultSuccess -> {
                if (result.data.isNullOrEmpty()) ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
                else SuccessState(result.data?.toModelStudentList())
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
    private fun handleResponse(error: FailureService): ModelState<List<ModelStudent?>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    // Función para convertir una lista de ResponseGetStudent a ModelStudent
    private fun List<ResponseGetStudent?>.toModelStudentList(): List<ModelStudent> {
        return this.map { response ->
            ModelStudent(
                studentId = response?.studentId,
                curp = response?.curp,
                birthday = response?.birthday,
                phoneNumber = response?.phoneNumber,
                userId = response?.userId,
                name = response?.name,
                lastName = response?.lastName,
                secondLastName = response?.secondLastName
            )
        }
    }
}