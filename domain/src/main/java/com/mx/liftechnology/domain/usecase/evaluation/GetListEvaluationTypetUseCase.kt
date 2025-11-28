/**
 * @file Define el caso de uso para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeFormativeField
import com.mx.liftechnology.data.repository.evaluation.GetListWorkTypeFormativeFieldRepository
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult


/**
 * Implementación de [GetListWorkEvaluationFormativeFieldUseCase].
 * Encapsula la lógica de negocio para solicitar la lista de tipos de evaluación y manejar la respuesta.
 *
 * @property getListWorkTypeFormativeFieldRepository El repositorio para obtener los tipos de evaluación.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListWorkEvaluationFormativeFieldUseCase (
    private val getListWorkTypeFormativeFieldRepository : GetListWorkTypeFormativeFieldRepository,
    private val preference: PreferenceUseCase
)  {

    /**
     * {@inheritDoc}
     */
    suspend operator fun invoke(): ModelResult<ModelWorkTypeFormativeField, ModelError> {
        val formativeFieldId = preference.getPreferenceInt(ModelPreference.ID_FORMATIVE_FIELD)?: return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        return runCatching { getListWorkTypeFormativeFieldRepository.executeGetListWorkTypeFormativeField(
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