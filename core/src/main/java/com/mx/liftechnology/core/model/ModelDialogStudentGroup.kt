package com.mx.liftechnology.core.model

import com.mx.liftechnology.core.network.callapi.ResponseGroupTeacher

data class ModelDialogStudentGroup (
    val selected : Boolean,
    val item : ResponseGroupTeacher?,
    val nameItem : String?
)