package com.mx.liftechnology.data.model.schoolCycle

data class ModelCCTData(
    val id: Int,
    val cct: String,
    val schoolTypeId: Int,
    val schoolName: String,
    val shiftName: String?,
    val periodCatalog: List<ModelCCTDataPeriodCatalog>
)
data class ModelCCTDataPeriodCatalog(
    val id: Int,
    val typeName: String,
    val periodNumber: Int
)