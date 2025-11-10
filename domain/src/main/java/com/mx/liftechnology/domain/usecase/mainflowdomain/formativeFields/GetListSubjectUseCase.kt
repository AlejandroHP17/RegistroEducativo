package com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.formativeFields.GetListFormativeFieldsRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.ModelFormatFormativeFieldsDomain
import com.mx.liftechnology.domain.model.formativeFields.toModelListFormativeFields

/**
 * Caso de uso para obtener la lista de materias.
 * Encapsula la lógica de negocio para solicitar la lista de materias, procesarla y manejar los errores.
 *
 * @property getListFormativeFieldsRepository El repositorio para obtener la lista de materias.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectUseCase (
    private val getListFormativeFieldsRepository : GetListFormativeFieldsRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Ejecuta el proceso para obtener la lista de materias.
     *
     * @return Un [ModelResult] que contiene la lista de materias o un estado de error.
     */
    suspend operator fun invoke(): ModelResult<List<ModelFormatFormativeFieldsDomain>?, Error> {
        val cycleSchoolId = preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if(cycleSchoolId == null) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        return runCatching { getListFormativeFieldsRepository.executeGetListFormativeFields(cycleSchoolId) }.fold(
            onSuccess = { result ->
                when(result){
                    is SuccessResult -> {
                        if (result.data.isNullOrEmpty()) ErrorResult(LocalError.EMPTY)
                        else SuccessResult(result.data.toModelListFormativeFields())
                    }
                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkError.UNKNOWN)}
        )
    }
}