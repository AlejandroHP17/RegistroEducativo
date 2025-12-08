/**
 * @file Define el modelo de dominio para los spinners del formulario de registro de escuela.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.registerschool

import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner

/**
 * Modelo de datos para poblar los spinners en el formulario de registro de escuela.
 *
 * @property type La lista de tipos de periodo disponibles (ej: anual, semestral).
 * @property cycle La lista de ciclos escolares disponibles (ej: 1, 2, 3).
 * @property grade La lista de grados disponibles (ej: 1, 2, 3).
 * @property group La lista de grupos disponibles (ej: A, B, C).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class SchoolSpinnerDomain(
    val type: List<ModelCustomSpinner>?, // anual, etc
    val cycle: List<ModelCustomSpinner>?, // 1, 2, 3, etc
    val grade: List<ModelCustomSpinner>?, // 1, 2, 3, etc
    val group: List<ModelCustomSpinner>? // a,b ,c, etc
)
