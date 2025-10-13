package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGroupTeacher

/**
 * Data model for a student group dialog in the domain layer.
 *
 * @property selected Indicates whether the item is selected.
 * @property item The [ResponseGroupTeacher] object associated with this dialog item.
 * @property nameItem The display name of the item.
 * @property listItemPartial A list of partials associated with the group.
 * @property itemPartial The currently selected partial.
 * @property namePartial The name of the selected partial.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelDialogStudentGroupDomain (
    val selected : Boolean?,
    val item : ResponseGroupTeacher?,
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
val List<ResponseGroupTeacher?>?.RGTtoConvertModelDialogStudentGroupDomains: List<ModelDialogStudentGroupDomain>
    get() = this?.mapNotNull { teacher -> // Filtramos los valores nulos
        teacher?.let {
            ModelDialogStudentGroupDomain(
                selected = false,
                item = teacher,
                nameItem = "${it.cct} - ${it.group}${it.name} - ${it.shift}",
                listItemPartial = null,
                itemPartial = null,
                namePartial = null
            )
        }
    } ?: emptyList()