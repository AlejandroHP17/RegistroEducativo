package com.mx.liftechnology.registroeducativo.main.ui.components.calendars

import android.icu.util.Calendar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mx.liftechnology.core.util.extension.logInfo
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCalendar
import com.mx.liftechnology.registroeducativo.main.ui.components.buttons.ButtonsCalendar
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorApprove
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorDisabled
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorSuccess
import com.mx.liftechnology.registroeducativo.main.util.getCandidate
import com.mx.liftechnology.registroeducativo.main.util.toLocalDate
import com.mx.liftechnology.registroeducativo.main.util.toMillis
import java.time.LocalDate

/**
 * Diálogo para seleccionar un rango de fechas.
 *
 * @param showDialog Si el diálogo está visible.
 * @param onDismiss Lambda que se invoca cuando se cierra el diálogo.
 * @param onDateSelected Lambda que se invoca cuando se selecciona un rango de fechas.
 */
@Composable
fun DateRangePickerDialog(
    index: Int,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    rangeDate : List<Pair<LocalDate,LocalDate>?>?,
    onDateSelected: (startDate: LocalDate, endDate: LocalDate) -> Unit,
) {

    var startDate by remember{ mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    var colorStart by remember { mutableStateOf(colorDisabled) }
    var colorEnd by remember { mutableStateOf(colorDisabled) }
    var isEnable by remember { mutableStateOf(false) }

    var selectingState by remember { mutableStateOf(true) }

    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]

    val datePickerStateStart = rememberDatePickerState(
        yearRange = year - 2 .. year + 2,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val candidate = getCandidate(utcTimeMillis)

                val aux = rangeDate?.let { it2 ->
                    if(it2.size > 1 && index > 0){
                        it2[index-1]?.second?.let { candidate > it }?: true
                    }
                    else true
                }?:true
                logInfo("${(endDate?.let { candidate < it } ?: true) && aux}", "pelki")
                return (endDate?.let { candidate < it } ?: true) && aux
            }
        }
    )
    val datePickerStateEnd = rememberDatePickerState(
        yearRange = year - 2 .. year + 2,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val candidate = getCandidate(utcTimeMillis)
                val aux = rangeDate?.let { it2 ->
                    if(it2.size > 1 && it2.size == index){
                        it2[index+1]?.first?.let { candidate < it }?: true
                    }
                    else true
                }?:true
                return (startDate?.let { candidate >= it } ?: true) && aux
            }
        }
    )

    datePickerStateStart.selectedDateMillis?.let { millis ->
        val localDate: LocalDate = getCandidate(millis)
        startDate = localDate
        colorStart = colorApprove
        isEnable = colorEnd == colorApprove
    }

    datePickerStateEnd.selectedDateMillis?.let { millis ->
        val localDate: LocalDate = getCandidate(millis)
        endDate = localDate
        colorEnd = colorApprove
        isEnable = colorStart == colorApprove
    }


    if(showDialog){
        DatePickerDialog (
            onDismissRequest = { onDismiss() },
            confirmButton = {
                ButtonsCalendar(
                    colorStart = colorStart,
                    colorEnd = colorEnd,
                    colorContinue = colorSuccess,
                    disabledContinue = isEnable,
                    onActionClick = {
                        when (it){
                            0->{
                                datePickerStateStart.selectedDateMillis = startDate?.toMillis()
                                if (startDate != null) {
                                    datePickerStateStart.displayedMonthMillis =
                                        startDate!!.withDayOfMonth(1).toMillis()
                                }
                                selectingState = true
                            }
                            1->{
                                datePickerStateEnd.selectedDateMillis = endDate?.toMillis()
                                if (endDate != null) {
                                    datePickerStateEnd.displayedMonthMillis =
                                        endDate!!.withDayOfMonth(1).toMillis()
                                }
                                selectingState = false
                            }
                            2-> {
                                onDateSelected(startDate!!, endDate!!)
                                onDismiss()
                            }
                        }
                    },
                    onDismiss = {}
                )

            }, colors = DatePickerDefaults.colors()
        ){
            DatePicker(
                state = if (selectingState) datePickerStateStart else datePickerStateEnd
            )
        }
    }
}

/**
 * Diálogo para seleccionar una fecha única.
 *
 * @param showDialog Si el diálogo está visible.
 * @param dialogState El estado del diálogo.
 * @param onDismiss Lambda que se invoca cuando se cierra el diálogo.
 * @param onDateSelected Lambda que se invoca cuando se selecciona una fecha.
 */
@Composable
fun DateSimplePickerDialog(
    showDialog: Boolean,
    dialogState: CustomCalendar?,
    onDismiss: () -> Unit,
    onDateSelected: (date: LocalDate) -> Unit,
) {

    var date by remember{ mutableStateOf<LocalDate?>(null) }
    var isEnable by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val candidate = getCandidate(utcTimeMillis)

                val split = dialogState?.rangeDate?.split("/")
                return split?.let {
                    val start = it[0].toLocalDate()
                    val end = it[1].toLocalDate()
                    candidate in start..end
                } ?: true
            }
        }
    )

    LaunchedEffect(Unit) {
        datePickerState.displayMode = DisplayMode.Input
    }

    datePickerState.selectedDateMillis?.let { millis ->
        val localDate: LocalDate = getCandidate(millis)
        date = localDate
        isEnable = true
    }

    if(showDialog){
        DatePickerDialog (
            onDismissRequest = { onDismiss() },
            confirmButton = {
                OutlinedButton(
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colorSuccess,
                        contentColor = colorPrincipalText,
                        disabledContainerColor = colorDisabled,
                        disabledContentColor = colorPrincipalText
                    ),
                    enabled = isEnable,
                    onClick = {
                        onDateSelected(date!!)
                        onDismiss()
                    }
                )
                { Text("Confirmar") }

            }, colors = DatePickerDefaults.colors()
        ){
            DatePicker(
                state = datePickerState
            )
        }
    }
}