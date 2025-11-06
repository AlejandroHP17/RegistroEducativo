package com.mx.liftechnology.registroeducativo.main.util

import com.mx.liftechnology.data.model.ModelCCTDataPeriodCatalog

fun List<ModelCCTDataPeriodCatalog>?.toSelectPeriod(cycle: String, type: String): Int {
    return this!!
        .firstOrNull { it.typeName == type && it.periodNumber == cycle.toInt() }
        ?.id?: 0
}