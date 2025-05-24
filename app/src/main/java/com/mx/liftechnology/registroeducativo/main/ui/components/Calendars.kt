package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.datepicker.MaterialDatePicker
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_principal_text
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_secondary_text
import com.mx.liftechnology.registroeducativo.main.ui.theme.color_white
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DateRangePickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (startDate: LocalDate, endDate: LocalDate) -> Unit,
) {
    val context = LocalContext.current

    if (showDialog) {
        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Selecciona un rango de fechas")
            .build()

        picker.addOnPositiveButtonClickListener { selection ->
            val startDate = selection.first?.let {
                Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
            }
            val endDate = selection.second?.let {
                Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
            }

            if (startDate != null && endDate != null) {
                onDateSelected(startDate, endDate)
            }
        }

        picker.addOnDismissListener { onDismiss() }

        picker.show((context as AppCompatActivity).supportFragmentManager, "DATE_PICKER")


    }
}

enum class ModelStepCalendar(){
    START,
    FINISH
}

@Composable
fun CustomDateRangePicker(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (startDate: LocalDate, endDate: LocalDate) -> Unit,
) {
    val dateDialogState = rememberMaterialDialogState()

    var startDate by remember{ mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var stepCalendar by remember { mutableStateOf(ModelStepCalendar.START) }

    if (showDialog && !dateDialogState.showing) {
        // Asegura mostrar el diálogo sólo una vez
        dateDialogState.show()
    }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            startDate = null
            endDate = null
            stepCalendar = ModelStepCalendar.START
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        onCloseRequest = {
            onDismiss()
        },
        buttons = {
            positiveButton(
                text =
                    if (stepCalendar == ModelStepCalendar.FINISH) "Aceptar"
                    else  "Siguiente"
            ) {
                if (stepCalendar == ModelStepCalendar.START) {
                    stepCalendar = ModelStepCalendar.FINISH
                }
                else {
                    if (startDate != null && endDate != null) {
                        onDateSelected(startDate!!, endDate!!)
                        startDate = null
                        endDate = null
                        dateDialogState.hide()
                        onDismiss()
                    }
                }
            }

            negativeButton(
                text = if (stepCalendar == ModelStepCalendar.START) "Cancelar" else "Atrás"
            ) {
                if (stepCalendar == ModelStepCalendar.START) {
                    startDate = null
                    endDate = null
                    stepCalendar = ModelStepCalendar.START
                    dateDialogState.hide()
                    onDismiss()
                } else {
                    stepCalendar = ModelStepCalendar.START
                    endDate = null
                }
            }
        }
    ) {
        datepicker(
            title = if (stepCalendar == ModelStepCalendar.START) "Selecciona la fecha inicial" else "Selecciona la fecha final",
            initialDate = LocalDate.now(),
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = color_principal_text,
                headerTextColor = color_white,
                calendarHeaderTextColor = color_principal_text,
                dateActiveBackgroundColor = color_principal_text,
                dateInactiveTextColor = color_secondary_text,
                dateActiveTextColor = color_white
            )
        ) { date ->
            if (stepCalendar == ModelStepCalendar.START) {
                startDate = date
            } else {
                endDate = date
            }
        }
    }
}