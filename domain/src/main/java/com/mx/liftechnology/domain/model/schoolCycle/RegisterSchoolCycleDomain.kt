package com.mx.liftechnology.domain.model.schoolCycle

data class RegisterSchoolCycleDomain(
    val teacherId: Int?,
    val schoolId: Int?,
    val name: String?,
    val isActive: Boolean?,
    val idCycleSchool: Int,
)