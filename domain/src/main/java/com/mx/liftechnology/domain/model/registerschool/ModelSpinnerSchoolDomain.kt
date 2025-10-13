package com.mx.liftechnology.domain.model.registerschool

/**
 * Data model for populating spinners in the school registration form.
 *
 * @property cycle The list of available school cycles.
 * @property grade The list of available grades.
 * @property group The list of available groups.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelSpinnerSchoolDomain(
    val cycle: List<String>?,
    val grade: List<String>?,
    val group: List<String>?
)
