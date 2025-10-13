package com.mx.liftechnology.domain.model

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Data model representing a date period in the domain layer.
 * Used for selecting and managing date ranges.
 *
 * @property position The position of the date period, typically in a list.
 * @property date The state of the date input field, including its value and any validation messages.
 * @property partialCycleGroup The identifier for the partial school cycle group this period belongs to.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelDatePeriodDomain(
    val position:Int,
    val date: ModelStateOutFieldText =
        ModelStateOutFieldText(
            valueText = "",
            isError = false,
            errorMessage = ""
        ),
    val partialCycleGroup: Int?,
)
