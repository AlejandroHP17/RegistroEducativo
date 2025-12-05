package com.mx.liftechnology.domain.usecase.share

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository

/**
 * Caso de uso para obtener la lista de materias.
 * Encapsula la lógica de negocio para solicitar la lista de materias, procesarla y manejar los errores.
 *
 * @property formativeFieldRepository El repositorio para operaciones relacionadas con campos formativos.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListFormativeFieldUseCase (
    private val formativeFieldRepository: FormativeFieldRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Ejecuta el proceso para obtener la lista de materias.
     *
     * @return Un [ModelResult] que contiene la lista de materias o un estado de error.
     */
    suspend operator fun invoke(): ModelResult<List<FormativeFieldDomain>?, ModelError> {
        val cycleSchoolId = preference.getIdCycleSchool()

        if(cycleSchoolId == null) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        val result = formativeFieldRepository.getList(cycleSchoolId)
        return when (result) {
            is SuccessResult -> {
                if (result.data.isEmpty()) ErrorResult(LocalModelError.EMPTY)
                else SuccessResult(result.data)
            }
            is ErrorResult -> result
        }
    }
}