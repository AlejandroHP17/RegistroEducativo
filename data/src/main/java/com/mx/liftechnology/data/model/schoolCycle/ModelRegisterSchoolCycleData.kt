package com.mx.liftechnology.data.model.schoolCycle

data class ModelRegisterSchoolCycleData(
    val teacherId: Int?,
    val schoolId: Int?,
    val name: String?,
    val description: String?,
    val isActive: Boolean?,
    val idCycleSchool: Int,
)