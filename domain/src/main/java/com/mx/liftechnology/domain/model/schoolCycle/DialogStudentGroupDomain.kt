/**
 * @file Define los modelos de datos para los diálogos de selección de grupo y parcial.
 * @author Pelkidev
 * @version 1.0.0
 */

package com.mx.liftechnology.domain.model.schoolCycle

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
data class DialogStudentGroupDomain (
    val selected : Boolean?,
    val item : SchoolCycleDomain?,
    val nameItem : String?,
    val listItemPartial : List<DialogGroupPartialDomain>?,
    val itemPartial: DialogGroupPartialDomain?,
    val namePartial : String?
)

/**
 * Propiedad de extensión para convertir una lista de [SchoolCycleDomain] a una lista de [DialogStudentGroupDomain].
 *
 * @receiver Una lista de objetos [SchoolCycleDomain].
 * @return Una lista de objetos [DialogStudentGroupDomain].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val List<SchoolCycleDomain>.toDialogStudentGroupDomainList: List<DialogStudentGroupDomain>
    get() = this.map { cycle ->
        DialogStudentGroupDomain(
            selected = false,
            item = cycle,
            nameItem = cycle.name,
            listItemPartial = null,
            itemPartial = null,
            namePartial = null
        )
    }