/**
 * @file Define el caso de uso para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.core.network.apiCall.evaluation.RequestGetListEvaluationType
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.evaluation.GetListEvaluationTypeRepository
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult

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
     * @return Un [ModelResult] que contiene la lista de tipos de evaluación (como `String`) o un estado de error.
     */
    suspend fun getListEvaluationType(): ModelResult<List<String>?, ModelError>?
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
    override suspend fun getListEvaluationType(): ModelResult<List<String>?, ModelError> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_USER_LEVEL)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        val request = RequestGetListEvaluationType(
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return runCatching { getListEvaluationTypeRepository.executeGetListEvaluationType(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        if (result?.data?.isNullOrEmpty() == true) ErrorResult(LocalModelError.EMPTY)
                        else SuccessResult(result.data)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }
}