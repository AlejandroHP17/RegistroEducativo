package com.mx.liftechnology.domain.model

import com.mx.liftechnology.core.network.callapi.ResponseGroupTeacher

data class ModelDialogStudentGroup (
    val selected : Boolean?,
    val item : ResponseGroupTeacher?,
    val nameItem : String?
)

val List<ResponseGroupTeacher?>?.RGTtoConvertModelDialogStudentGroup: List<ModelDialogStudentGroup>
    get() = this?.mapNotNull { teacher -> // Filtramos los valores nulos
        teacher?.let {
            ModelDialogStudentGroup(
                selected = false,
                item = teacher,
                nameItem = "${it.cct} - ${it.group}${it.name} - ${it.shift}"
            )
        }
    } ?: emptyList()