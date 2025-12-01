package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.SpinnerSchoolUi

/**
 * Mapper para convertir modelos del dominio de ciclos escolares a modelos de UI.
 * Incluye validaciones para asegurar la integridad de los datos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object SchoolCycleMapper {

    /**
     * Convierte ModelSpinnerSchoolDomain a SpinnerSchoolUi.
     * Valida que los datos del spinner sean válidos antes de mapearlos.
     *
     * @param spinnerSchoolDomain El modelo del dominio con los datos del spinner.
     * @return Un SpinnerSchoolUi con los datos formateados para la UI.
     */
    fun ModelSpinnerSchoolDomain?.toUi(): SpinnerSchoolUi {
        if (this == null) {
            return SpinnerSchoolUi(
                type = null,
                cycle = null,
                grade = null,
                group = null
            )
        }
        
        return SpinnerSchoolUi(
            type = this.type?.mapNotNull { typeItem ->
                if (typeItem.value.isNullOrBlank()) null
                else ModelCustomSpinner(id = 0, value = typeItem.value)
            }?.takeIf { it.isNotEmpty() },
            cycle = this.cycle?.mapNotNull { cycleItem ->
                if (cycleItem.value.isNullOrBlank()) null
                else ModelCustomSpinner(id = 0, value = cycleItem.value)
            }?.takeIf { it.isNotEmpty() },
            grade = this.grade?.mapNotNull { gradeItem ->
                if (gradeItem.value.isNullOrBlank()) null
                else ModelCustomSpinner(id = 0, value = gradeItem.value)
            }?.takeIf { it.isNotEmpty() },
            group = this.group?.mapNotNull { groupItem ->
                if (groupItem.value.isNullOrBlank()) null
                else ModelCustomSpinner(id = 0, value = groupItem.value)
            }?.takeIf { it.isNotEmpty() }
        )
    }
}

