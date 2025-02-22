package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.domain.model.ModelDialogStudentGroup

data class ModelInfoMenu(
    val listSchool: List<ModelDialogStudentGroup>,
    val infoSchoolSelected: ModelDialogStudentGroup
)