package com.mx.liftechnology.domain.model.schoolCycle

data class CCTDomain(
    val id: Int,
    val cct: String,
    val schoolTypeId: Int,
    val schoolName: String,
    val shiftName: String?,
    val periodCatalog: List<CCTPeriodCatalogDomain>
)
data class CCTPeriodCatalogDomain(
    val id: Int,
    val typeName: String,
    val periodNumber: Int
)