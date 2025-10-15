package com.mx.liftechnology.domain.model.registerschool

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
    val cycle: List<String>?,
    val grade: List<String>?,
    val group: List<String>?
)
