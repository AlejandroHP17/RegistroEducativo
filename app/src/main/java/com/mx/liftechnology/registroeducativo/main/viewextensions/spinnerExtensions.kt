package com.mx.liftechnology.registroeducativo.main.viewextensions

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.util.ModelSpinnerSelect

fun Spinner.fillItem(requireContext: Context, model: ModelSpinnerSelect, validateString: String?) : String {
    val spinner: Spinner = this

    /** Validate the origin*/
    val options: Array<String> = when(model) {
        ModelSpinnerSelect.CYCLE -> selectCycle(validateString)
        ModelSpinnerSelect.GRADE -> selectGrade(validateString)
        ModelSpinnerSelect.GROUP -> selectGroup(validateString)
        ModelSpinnerSelect.PERIOD -> requireContext.resources.getStringArray(R.array.spinner_period)
        ModelSpinnerSelect.SUBJECT -> requireContext.resources.getStringArray(R.array.spinner_subject)
    }

    /** Create the adapter */
    val adapter = ArrayAdapter(
        requireContext,
        android.R.layout.simple_spinner_item,
        options
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter
    return options[0]
}

private fun selectCycle(validateString: String?): Array<String> {
    val cycleMapping = mapOf(
        "Anual" to 1,
        "Bimestral" to 2,
        "Trimestral" to 3,
        "Cuatrimestral" to 4
    )

    val cycleCount = cycleMapping[validateString] ?: return emptyArray()
    return (1..cycleCount).map { it.toString() }.toTypedArray()
}

private fun selectGrade(validateString: String?): Array<String> {
    val gradeMapping = mapOf(
        "Primaria" to 6,
        "Secundaria" to 3,
        "Bachillerato" to 6,
        "Univesidad" to 12
    )
    val gradeCount = gradeMapping[validateString] ?: return emptyArray()
    return (1..gradeCount).map { "$it°" }.toTypedArray()
}

private fun selectGroup(validateString: String?): Array<String> {
    val groupMapping = mapOf(
        "Primaria" to 4,
        "Secundaria" to 12,
        "Bachillerato" to 10,
        "Univesidad" to 10
    )

    val groupCount = groupMapping[validateString] ?: return emptyArray()

    // Generamos el arreglo de letras basado en el número de letras requerido
    return ('A'..'Z').take(groupCount).map { it.toString() }.toTypedArray()
}