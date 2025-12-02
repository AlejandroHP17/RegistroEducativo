package com.mx.liftechnology.domain.usecase.formativeField


import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.formativeField.GetListFormativeFieldRepository
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.toFormativeFieldDomainList

/**
 * Caso de uso para obtener la lista de materias.
 * Encapsula la lógica de negocio para solicitar la lista de materias, procesarla y manejar los errores.
 *
 * @property getListFormativeFieldRepository El repositorio para obtener la lista de materias.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectUseCase (
    private val getListFormativeFieldRepository : GetListFormativeFieldRepository,
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

        val result = getListFormativeFieldRepository.getList(cycleSchoolId)
        return when (result) {
            is SuccessResult -> {
                if (result.data.isEmpty()) ErrorResult(LocalModelError.EMPTY)
                else SuccessResult(result.data.toFormativeFieldDomainList())
            }
            is ErrorResult -> result
        }
    }
}