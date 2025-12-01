package com.mx.liftechnology.domain.usecase.schoolCycle.menu

import com.mx.liftechnology.core.preference.PreferenceKeys
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.schoolCycle.ModelDialogGroupPartialDomain

/**
 * @file Define el caso de uso para actualizar el parcial seleccionado.
 * @author Pelkidev
 * @version 1.0.0
 */
/**
 * Caso de uso para actualizar el parcial seleccionado.
 * Encapsula la lógica para guardar el ID del parcial seleccionado en las preferencias.
 *
 * @property preference El caso de uso para gestionar las preferencias.
 * @author Pelkidev
 * @version 1.0.0
 */
class UpdatePartialMenuUseCase(
    private val preference: PreferenceUseCase,
) {

    /**
     * Actualiza el parcial seleccionado en las preferences.
     *
     * @param partial de [com.mx.liftechnology.domain.model.schoolCycle.ModelDialogGroupPartialDomain] representa el parcial seleccionado.
     */
    operator fun invoke(partial: ModelDialogGroupPartialDomain?) {
        preference.savePreferenceInt(
            PreferenceKeys.ID_PARTIAL,
            partial?.partialId?: -1
        )
        val result = ("${partial?.startDate}/${partial?.endDate}")
        preference.savePreferenceString(PreferenceKeys.RANGE_DATES_PARTIAL, result)
    }

}