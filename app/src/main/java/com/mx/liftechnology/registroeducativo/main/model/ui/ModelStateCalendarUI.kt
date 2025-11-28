package com.mx.liftechnology.registroeducativo.main.model.ui

/**
 * Representa los callbacks para las interacciones con el calendario.
 *
 * @property onDateSelected Lambda que se invoca al seleccionar una fecha.
 * @property onDismiss Lambda que se invoca al descartar el diálogo.
 * @author Pelkidev
 * @version 1.0.0
 */
data class CalendarUiCallbacks(
    val onDateSelected: (Long) -> Unit,
    val onDismiss: () -> Unit
)
