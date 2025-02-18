package com.mx.liftechnology.domain.converters

import com.mx.liftechnology.core.model.ModelDialogStudentGroup
import com.mx.liftechnology.core.network.callapi.ResponseGroupTeacher


val List<ResponseGroupTeacher?>?.RGTtoConvertModelDialogStudentGroup: List<ModelDialogStudentGroup>
    get() = this?.mapNotNull { teacher -> // Filtramos los valores nulos
        teacher?.let {
            ModelDialogStudentGroup(
                selected = false,
                item = teacher,
                nameItem = "${it.cct} - ${it.group} ${it.name} - ${it.shift}"
            )
        }
    } ?: emptyList()