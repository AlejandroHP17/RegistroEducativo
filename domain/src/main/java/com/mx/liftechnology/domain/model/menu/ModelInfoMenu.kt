package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.core.model.modelApi.DataGroupTeacher

data class ModelInfoMenu(
   val listSchool: List<DataGroupTeacher?>?,
   val infoSchoolSelected: DataGroupTeacher,
   val infoShowSchool : String
)