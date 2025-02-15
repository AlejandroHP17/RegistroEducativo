package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.core.network.callapi.ResponseGroupTeacher

data class ModelInfoMenu(
   val listSchool: List<ResponseGroupTeacher?>?,
   val infoSchoolSelected: ResponseGroupTeacher,
   val infoShowSchool : String
)