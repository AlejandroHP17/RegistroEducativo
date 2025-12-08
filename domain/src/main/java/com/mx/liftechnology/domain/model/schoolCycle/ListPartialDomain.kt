package com.mx.liftechnology.domain.model.schoolCycle

data class ListPartialDomain(
    val description: String,
    val startDate: String?,
    val endDate: String?,
    val partialId : Int,
)