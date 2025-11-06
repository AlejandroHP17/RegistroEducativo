package com.mx.liftechnology.domain.model.registerschool

import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner

/**
 * Modelo de datos para poblar los spinners en el formulario de registro de escuela.
 *
 * @property cycle La lista de ciclos escolares disponibles.
 * @property grade La lista de grados disponibles.
 * @property group La lista de grupos disponibles.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelSpinnerSchoolDomain(
    val type: List<ModelCustomSpinner>?, // anual, etc
    val cycle: List<ModelCustomSpinner>?, // 1, 2, 3, etc
    val grade: List<ModelCustomSpinner>?, // 1, 2, 3, etc
    val group: List<ModelCustomSpinner>? // a,b ,c, etc
)
