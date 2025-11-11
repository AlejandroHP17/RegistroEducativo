/**
 * @file Define el caso de uso para actualizar el parcial seleccionado.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.schoolCycle.partial

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.schoolCycle.ModelDialogGroupPartialDomain

/**
 * Caso de uso para actualizar el parcial seleccionado.
 * Encapsula la lógica para guardar el ID del parcial seleccionado en las preferencias.
 *
 * @property preference El caso de uso para gestionar las preferencias.
 * @author Pelkidev
 * @version 1.0.0
 */
class UpdatePartialUseCase(
    private val preference: PreferenceUseCase,
) {

    /**
     * Actualiza el parcial seleccionado en las preferences.
     *
     * @param partial de [ModelDialogGroupPartialDomain] representa el parcial seleccionado.
     */
    operator fun invoke(partial: ModelDialogGroupPartialDomain?) {
        preference.savePreferenceInt(
            ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP,
            partial?.partialId?: -1
        )
        val result = ("${partial?.startDate}/${partial?.endDate}")
        preference.savePreferenceString(ModelPreference.RANGE_DATES_PARTIAL, result)
    }

}