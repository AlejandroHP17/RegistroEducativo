package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase

/**
 * @file Define el caso de uso para guardar el ID de la materia seleccionada.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Caso de uso para guardar el ID de la materia seleccionada en las preferencias del usuario.
 * Encapsula la lógica para persistir el ID de la materia para su uso posterior en la aplicación.
 *
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SaveIdSubjectSelectedUseCase (
    private val preference: PreferenceUseCase
){
    /**
     * Guarda el ID de la materia seleccionada en las preferencias.
     *
     * @param id El ID de la materia a guardar. Si es nulo, se guarda un valor por defecto (-1).
     */
    operator fun invoke(id: Int?) {
        preference.savePreferenceInt(
            ModelPreference.ID_FORMATIVE_FIELD,
            id?:-1
        )
    }
}