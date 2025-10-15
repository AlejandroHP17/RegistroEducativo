package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase

/**
 * @file Define el caso de uso para obtener el rango de fechas del parcial activo.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Caso de uso para obtener el rango de fechas del parcial actualmente activo.
 * Encapsula la lógica para recuperar esta información desde las preferencias del usuario.
 *
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetDatesActivePartialUseCase (private val preference: PreferenceUseCase) {

    /**
     * Ejecuta el proceso para obtener el rango de fechas del parcial activo.
     *
     * @return Un `String` que representa el rango de fechas (ej: "2024-01-01/2024-02-28"), o `null` si no se encuentra.
     */
    suspend operator fun invoke() : String? {
        return preference.getPreferenceString(ModelPreference.RANGE_DATES_PARTIAL)
    }
}