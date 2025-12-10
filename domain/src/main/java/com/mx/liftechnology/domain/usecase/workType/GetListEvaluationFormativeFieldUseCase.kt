/**
 * @file Define el caso de uso para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.workType


import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository


/**
 * Implementación de [GetListWorkEvaluationFormativeFieldUseCase].
 * Encapsula la lógica de negocio para solicitar la lista de tipos de evaluación y manejar la respuesta.
 *
 * @property evaluationRepository El repositorio para operaciones relacionadas con evaluaciones.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListWorkEvaluationFormativeFieldUseCase (
    private val evaluationRepository: EvaluationRepository,
    private val preference: PreferenceUseCase
)  {

    /**
     * {@inheritDoc}
     */
    suspend operator fun invoke(): ModelResult<WorkTypeFormativeFieldDomain, ModelError> {
        val formativeFieldId = preference.getIdFormativeField()?: return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        return runCatching { evaluationRepository.getListWorkTypeFormativeField(
            formativeFieldId) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                         SuccessResult(result.data)
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