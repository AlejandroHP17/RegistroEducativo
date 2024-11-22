package com.mx.liftechnology.registroeducativo.main.viewextensions

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.util.ModelSpinnerSelect

fun Spinner.fillItem(requireContext: Context, model: ModelSpinnerSelect) {
    val spinner: Spinner = this

    /** Validate the origin*/
    val options: Array<String> = when(model) {
        ModelSpinnerSelect.TYPE -> requireContext.resources.getStringArray(R.array.spinner_type)
        ModelSpinnerSelect.CYCLE -> requireContext.resources.getStringArray(R.array.spinner_cycle)
        ModelSpinnerSelect.GRADE -> requireContext.resources.getStringArray(R.array.spinner_grade)
        ModelSpinnerSelect.GROUP -> requireContext.resources.getStringArray(R.array.spinner_group)
        ModelSpinnerSelect.PERIOD -> requireContext.resources.getStringArray(R.array.spinner_period)
    }

    /** Create the adapter */
    val adapter = ArrayAdapter(
        requireContext,
        android.R.layout.simple_spinner_item,
        options
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter
}
