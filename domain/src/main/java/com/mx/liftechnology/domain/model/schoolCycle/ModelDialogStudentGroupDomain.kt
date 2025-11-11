/**
 * @file Define los modelos de datos para los diálogos de selección de grupo y parcial.
 * @author Pelkidev
 * @version 1.0.0
 */

package com.mx.liftechnology.domain.model.schoolCycle

import com.mx.liftechnology.core.network.apiCall.schoolCycle.ResponseGroupTeacher
import com.mx.liftechnology.data.model.schoolCycle.ModelSchoolCycleData

/**
 * Modelo de datos que representa un grupo de estudiantes en un diálogo de selección.
 *
 * @property selected Indica si el grupo está seleccionado.
 * @property item La información del grupo obtenida de la respuesta de la API.
 * @property nameItem El nombre del ítem a mostrar en la UI.
 * @property listItemPartial La lista de parciales asociados a este grupo.
 * @property itemPartial El parcial actualmente seleccionado.
 * @property namePartial El nombre del parcial seleccionado.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelDialogStudentGroupDomain (
    val selected : Boolean?,
    val item : ModelSchoolCycleData?,
    val nameItem : String?,
    val listItemPartial : List<ModelDialogGroupPartialDomain>?,
    val itemPartial: ModelDialogGroupPartialDomain?,
    val namePartial : String?
)

/**
 * Extension property to convert a list of [ResponseGroupTeacher] to a list of [ModelDialogStudentGroupDomain].
 *
 * @receiver A nullable list of [ResponseGroupTeacher] objects.
 * @return A list of [ModelDialogStudentGroupDomain] objects.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val List<ModelSchoolCycleData>.RGTtoConvertModelDialogStudentGroupDomains: List<ModelDialogStudentGroupDomain>
    get() = this.map { cycle ->
        ModelDialogStudentGroupDomain(
            selected = false,
            item = cycle,
            nameItem = cycle.name,
            listItemPartial = null,
            itemPartial = null,
            namePartial = null
        )
    }