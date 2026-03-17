package com.mx.liftechnology.registroeducativo.main.model.calendar

import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState


/**
 * Representa el estado de la UI de la pantalla de calendario.
 *
 * @property showControl Indica si el área de control debe mostrarse.
 * @property uiState El estado actual de la UI (por ejemplo, cargando, error, éxito).
 * @property controlToast El estado para mostrar mensajes toast.
 * @property controlToast El estado para mostrar mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class CalendarUiState(
    val showControl: Boolean = false,
    val uiState: EnumUi = EnumUi.NOTHING,
    val controlToast : ToastUiState = ToastUiState(R.string.app_name,false),
    val date : String? = null,
)
