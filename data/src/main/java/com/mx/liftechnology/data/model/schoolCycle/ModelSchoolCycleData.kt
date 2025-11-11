package com.mx.liftechnology.data.model.schoolCycle

data class ModelSchoolCycleData(
    val teacherId: Int,
    val schoolId: Int,
    val name: String,
    val grade: String?,
    val group: String?,
    val isActive: Boolean?,
    val cycleSchoolId : Int,
)