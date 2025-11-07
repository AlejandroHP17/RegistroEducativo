package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestPercent
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSubject
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.RegisterSubjectRepository
import com.mx.liftechnology.data.util.ErrorResult as DataErrorResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult as DataSuccessResult
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods

/**
 * @file Define el caso de uso para registrar una nueva materia.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Caso de uso para registrar una única materia.
 * Encapsula la lógica de negocio para construir la petición de registro y manejar la respuesta del repositorio.
 *
 * @property registerSubjectRepository El repositorio para las operaciones de registro de materias.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterOneSubjectUseCase(
    private val registerSubjectRepository: RegisterSubjectRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Ejecuta el proceso de registro de una materia.
     *
     * @param updatedList La lista de métodos de trabajo y sus porcentajes.
     * @param name El nombre de la materia.
     * @return Un [ResultModel] que indica el resultado de la operación de registro.
     */
    suspend operator fun invoke(
        updatedList: MutableList<ModelSpinnersWorkMethods>?,
        name: String?
    ): ResultModel<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_USER_LEVEL)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        val listAdapter: MutableList<RequestPercent> = mutableListOf()
        updatedList?.forEach { data ->
            listAdapter.add(
                RequestPercent(
                    jobId = data.assessmentTypeId,
                    percent = data.percent.valueText.toInt(),
                    assessmentType = data.name.valueText
                )
            )
        }

        val request = RequestRegisterSubject(
            subject = name,
            options = updatedList?.size,
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            userId = userId,
            teacherId = roleId,
            percents = listAdapter
        )

        return runCatching {registerSubjectRepository.executeRegisterOneSubject(request)}.fold(
            onSuccess = { result ->
                when (result) {
                    is DataSuccessResult -> {
                        SuccessResult(result.data)
                    }

                    is DataErrorResult -> {
                        handleResponse(result.error)
                    }
                }
            },
            onFailure = {ErrorResult(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    /**
     * Maneja las respuestas de error del repositorio de registro de materias.
     *
     * @param error El objeto [NetworkError] que representa el error.
     * @return Un [ResultModel] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: NetworkError): ResultModel<List<String?>?, String> {
        return when (error) {
            NetworkError.BAD_REQUEST -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION)
            NetworkError.UNAUTHORIZED -> ErrorUnauthorizedResult(ModelCodeError.ERROR_UNAUTHORIZED)
            NetworkError.NOT_FOUND -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION)
            NetworkError.TIMEOUT -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}