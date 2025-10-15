/**
 * @file Define el caso de uso para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
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
 * Interfaz para el caso de uso que obtiene la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListEvaluationTypeUseCase {
    /**
     * Ejecuta el proceso para obtener la lista de tipos de evaluación.
     *
     * @return Un [ModelState] que contiene la lista de tipos de evaluación (como `String`) o un estado de error.
     */
    suspend fun getListEvaluationType(): ModelState<List<String>?, String>?
}

/**
 * Implementación de [GetListEvaluationTypeUseCase].
 * Encapsula la lógica de negocio para solicitar la lista de tipos de evaluación y manejar la respuesta.
 *
 * @property getListEvaluationTypeRepository El repositorio para obtener los tipos de evaluación.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
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
        }
    }

    /**
     * Maneja las respuestas de error del repositorio, convirtiendo un [FailureService] en un [ModelState] específico.
     *
     * @param error El objeto [FailureService] que representa el error de la capa de datos.
     * @return Un [ModelState] que representa el error específico para la capa de dominio/UI.
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