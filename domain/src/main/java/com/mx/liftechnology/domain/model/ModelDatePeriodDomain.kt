package com.mx.liftechnology.domain.model

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/** Model - to select the dates
 * @author pelkidev
 * @since 1.0.0
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
