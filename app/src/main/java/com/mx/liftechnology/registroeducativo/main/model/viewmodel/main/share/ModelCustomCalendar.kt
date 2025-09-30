package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

data class ModelCustomCalendar (
    val rangeYears : Pair<Int, Int>? = null,
    val rangeDate : String? = null,
    val date : ModelStateOutFieldText = "".stringToModelStateOutFieldText()
)