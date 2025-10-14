package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomSpinner

/**
 * Representa el estado de la UI para la pantalla de registro de parciales.
 *
 * @property uiState El estado general de la UI.
 * @property isAvailable Indica si la acción de registro está disponible.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelRegisterPartialUIState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val isAvailable: Boolean = true,
    val controlToast: ModelStateToastUI = ModelStateToastUI(R.string.app_name, false),
)

/**
 * Representa los datos de la UI para la pantalla de registro de parciales.
 *
 * @property numberPartials El número de parciales a registrar.
 * @property listCalendar La lista de periodos de fechas para los parciales.
 * @property listOptions La lista de opciones para el número de parciales.
 * @property read Indica si los campos son de solo lectura.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelRegisterPartialUIData(
    val numberPartials: ModelStateOutFieldText = ModelStateOutFieldText(),
    val listCalendar: List<ModelDatePeriodDomain>? = listOf(),
    val listOptions: List<ModelCustomSpinner> = listOf(
        ModelCustomSpinner("1", 1),
        ModelCustomSpinner("2", 2),
        ModelCustomSpinner("3", 3),
        ModelCustomSpinner("4", 4)
    ),
    val read: Boolean = false,
)
