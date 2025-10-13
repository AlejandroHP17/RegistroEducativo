package com.mx.liftechnology.domain.model.generic

/**
 * Data model representing the state of an output text field.
 * This is typically used to display text and validation status in the UI.
 *
 * @property valueText The current text value of the field.
 * @property isError Indicates whether there is a validation error.
 * @property errorMessage The error message to display if [isError] is true.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelStateOutFieldText(
    val valueText : String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)
