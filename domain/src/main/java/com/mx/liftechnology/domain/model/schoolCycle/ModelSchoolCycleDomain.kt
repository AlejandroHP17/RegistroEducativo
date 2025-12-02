package com.mx.liftechnology.domain.model.schoolCycle

data class ModelSchoolCycleDomain(
    val teacherId: Int,
    val schoolId: Int,
    val name: String,
    val grade: String?,
    val group: String?,
    val isActive: Boolean?,
    val cycleSchoolId : Int,
)