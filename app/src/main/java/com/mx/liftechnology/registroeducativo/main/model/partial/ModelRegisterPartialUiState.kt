package com.mx.liftechnology.registroeducativo.main.model.partial

import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import java.time.LocalDate

/**
 * Representa el estado de la UI para la pantalla de registro de parciales.
 *
 * @property uiState El estado general de la UI.
 * @property isAvailable Indica si la acción de registro está disponible.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterPartialUiState(
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val isAvailable: Boolean = true,
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),
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
data class RegisterPartialUiData(
    val numberPartials: ModelStateOutFieldText = ModelStateOutFieldText(),
    val listCalendar: List<DatePeriodDomain>? = listOf(),
    val listOptions: List<ModelCustomSpinner> = listOf(
        ModelCustomSpinner("1", 1),
        ModelCustomSpinner("2", 2),
        ModelCustomSpinner("3", 3),
        ModelCustomSpinner("4", 4),
        ModelCustomSpinner("5", 5),
    ),
    val read: Boolean = false,
    val rangeDate : List<Pair<LocalDate,LocalDate>?>? = null,
)
