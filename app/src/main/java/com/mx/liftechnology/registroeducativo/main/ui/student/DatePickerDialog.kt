package com.mx.liftechnology.registroeducativo.main.ui.student

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerDialog(val listener: (day: Int, month: Int, year: Int) -> Unit) :
    DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var picker : DatePickerDialog? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

      /*  if (birthdate.isEmpty()) {
            year = calendar[Calendar.YEAR]
            month = calendar[Calendar.MONTH]
            day = calendar[Calendar.DAY_OF_MONTH]
        } else {
            year = birthdate.split("/")[2].toInt()
            month = birthdate.split("/")[1].toInt() - 1
            day = birthdate.split("/")[0].toInt()
        }

        picker = DatePickerDialog(
            activity as Context,
            R.style.DatePickerTheme,
            this,
            year,
            month,
            day,
        )

        picker?.setButton(DatePickerDialog.BUTTON_POSITIVE, "Aceptar", picker)
        picker?.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancelar", picker)

        calendar[Calendar.YEAR] = calendar[Calendar.YEAR] - 18
        picker?.datePicker?.maxDate = calendar.timeInMillis
        calendar[Calendar.YEAR] = calendar[Calendar.YEAR] - 100
        picker?.datePicker?.minDate = calendar.timeInMillis*/

        //return picker as DatePickerDialog

        // Crea una nueva instancia de DatePickerDialog y la retorna
        return DatePickerDialog(requireContext(), this, year, month, day)
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        listener(day, month+1 , year)
    }


}