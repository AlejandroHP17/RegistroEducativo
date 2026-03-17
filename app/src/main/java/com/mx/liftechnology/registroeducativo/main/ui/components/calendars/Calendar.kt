package com.mx.liftechnology.registroeducativo.main.ui.components.calendars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCalendar
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorPrincipalText
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorTransparent
import com.mx.liftechnology.registroeducativo.main.ui.theme.colorWhite
import com.mx.liftechnology.registroeducativo.main.util.getCandidate
import com.mx.liftechnology.registroeducativo.main.util.toLocalDate
import java.time.LocalDate


/**
 * A screen that displays a date picker.
 *
 * @param dialogState The state of the dialog.
 * @param onDateSelected A lambda to be invoked when a date is selected.
 */
@Composable
fun DatePickerScreen(
    dialogState: CustomCalendar?,
    onDateSelected: (date: LocalDate) -> Unit,
) {

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
        onDateSelected(localDate)
    }

    DatePicker(
        state = datePickerState,
        colors = DatePickerDefaults.colors(
            containerColor = colorWhite,
            headlineContentColor = colorPrincipalText,
            weekdayContentColor = colorPrincipalText,
            selectedDayContainerColor = colorPrincipalText,
            selectedDayContentColor = colorWhite,
        ),
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colorTransparent)
            .padding(8.dp)
    )
}