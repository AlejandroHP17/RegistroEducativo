package com.mx.liftechnology.core.model

import com.mx.liftechnology.core.model.modelApi.DataGroupTeacher

data class ModelDialogStudentGroup (
    val selected : Boolean,
    val item : DataGroupTeacher?,
    val nameItem : String?
)