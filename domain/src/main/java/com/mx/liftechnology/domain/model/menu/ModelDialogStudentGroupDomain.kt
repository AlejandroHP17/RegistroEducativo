package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.core.network.callapi.ResponseGroupTeacher

data class ModelDialogStudentGroupDomain (
    val selected : Boolean?,
    val item : ResponseGroupTeacher?,
    val nameItem : String?
)

val List<ResponseGroupTeacher?>?.RGTtoConvertModelDialogStudentGroupDomains: List<ModelDialogStudentGroupDomain>
    get() = this?.mapNotNull { teacher -> // Filtramos los valores nulos
        teacher?.let {
            ModelDialogStudentGroupDomain(
                selected = false,
                item = teacher,
                nameItem = "${it.cct} - ${it.group}${it.name} - ${it.shift}"
            )
        }
    } ?: emptyList()