package com.mx.liftechnology.registroeducativo.main.ui.components

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DateRangePickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (startDate: LocalDate, endDate: LocalDate) -> Unit
) {
    val context = LocalContext.current

    if (showDialog) {
        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Selecciona un rango de fechas")
            .build()

        picker.addOnPositiveButtonClickListener { selection ->
            val startDate = selection.first?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() }
            val endDate = selection.second?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate() }

            if (startDate != null && endDate != null) {
                onDateSelected(startDate, endDate)
            }
        }

        picker.addOnDismissListener { onDismiss() }

        picker.show((context as AppCompatActivity).supportFragmentManager, "DATE_PICKER")


    }
}
