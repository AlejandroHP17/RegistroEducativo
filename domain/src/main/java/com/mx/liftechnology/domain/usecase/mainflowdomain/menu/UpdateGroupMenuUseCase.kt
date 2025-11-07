/**
 * @file Define el caso de uso para actualizar el grupo seleccionado en el menú.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain

/**
 * Caso de uso para actualizar el grupo seleccionado en el menú.
 * Encapsula la lógica para guardar el ID del grupo seleccionado en las preferencias.
 *
 * @property preference El caso de uso para gestionar las preferencias.
 * @author Pelkidev
 * @version 1.0.0
 */
class UpdateGroupMenuUseCase(private val preference: PreferenceUseCase) {
    /**
     * Invoca el caso de uso para actualizar el grupo seleccionado.
     *
     * @param selected El grupo seleccionado.
     */
    operator fun invoke(selected: ModelDialogStudentGroupDomain) {
        preference.savePreferenceInt(
            ModelPreference.ID_CYCLE_SCHOOL,
            selected.item?.cycleSchoolId
        )
    }
}