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
import com.mx.liftechnology.data.util.ErrorResult as DataErrorResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult as DataSuccessResult
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult

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
     * @return Un [ResultModel] que contiene la lista de tipos de evaluación (como `String`) o un estado de error.
     */
    suspend fun getListEvaluationType(): ResultModel<List<String>?, String>?
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
    override suspend fun getListEvaluationType(): ResultModel<List<String>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_USER_LEVEL)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        val request = RequestGetListEvaluationType(
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return when (val result =  getListEvaluationTypeRepository.executeGetListEvaluationType(request)) {
            is DataSuccessResult -> {
                SuccessResult(result.data)
            }
            is DataErrorResult -> {
                handleResponse(result.error)
            }
        }
    }

    /**
     * Maneja las respuestas de error del repositorio, convirtiendo un [NetworkError] en un [ResultModel] específico.
     *
     * @param error El objeto [NetworkError] que representa el error de la capa de datos.
     * @return Un [ResultModel] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: NetworkError): ResultModel<List<String>?, String> {
        return when (error) {
            NetworkError.BAD_REQUEST -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.UNAUTHORIZED -> ErrorUnauthorizedResult(ModelCodeError.ERROR_UNAUTHORIZED)
            NetworkError.NOT_FOUND -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.TIMEOUT -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}