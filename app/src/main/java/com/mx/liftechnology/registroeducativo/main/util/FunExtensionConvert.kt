package com.mx.liftechnology.registroeducativo.main.util

import com.mx.liftechnology.data.model.schoolCycle.ModelCCTDataPeriodCatalog
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner

fun List<ModelCCTDataPeriodCatalog>?.toSelectPeriod(cycle: String, type: String): Int {
    return this!!
        .firstOrNull { it.typeName == type && it.periodNumber == cycle.toInt() }
        ?.id?: 0
}

fun List<ModelCCTDataPeriodCatalog>.getPeriodsByType(typeName: String): List<ModelCustomSpinner> {
    return this
        .filter { it.typeName == typeName }
        .map { it.periodNumber }
        .distinct()
        .map { number ->
            ModelCustomSpinner(
                value = number.toString(),
                id = number
            )
        }
}