package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.core.model.ModelDialogStudentGroup

data class ModelInfoMenu(
   val listSchool: List<ModelDialogStudentGroup>,
   val infoSchoolSelected: ModelDialogStudentGroup
)