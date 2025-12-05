package com.mx.liftechnology.registroeducativo.main.model.share

import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Modelo de datos para un calendario personalizado en la UI.
 * Se utiliza para gestionar el estado de los diálogos de selección de fecha.
 *
 * @property rangeYears El rango de años seleccionable.
 * @property rangeDate El rango de fechas seleccionable.
 * @property date El estado del campo de texto de la fecha.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelCustomCalendar (
    val rangeYears : Pair<Int, Int>? = null,
    val rangeDate : String? = null,
    val date : ModelStateOutFieldText = "".stringToModelStateOutFieldText()
)