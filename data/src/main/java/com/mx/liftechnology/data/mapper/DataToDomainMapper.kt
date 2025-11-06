package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseRegisterCycleSchool
import com.mx.liftechnology.data.model.ModelCCTData
import com.mx.liftechnology.data.model.ModelCCTDataPeriodCatalog
import com.mx.liftechnology.data.model.ModelRegisterCycleData

object DataToDomainMapper {

    fun ResponseCctSchool.mapperToRegisterSchool(): ModelCCTData {
        return ModelCCTData(
            id = this.id,
            cct = this.cct,
            schoolTypeId = this.schoolTypeId,
            schoolName = this.schoolName,
            shiftName = this.shiftName,
            periodCatalog = this.periodCatalog.map {
                ModelCCTDataPeriodCatalog(
                id = it.id,
                typeName = it.typeName,
                periodNumber = it.periodNumber
                )
            }
        )
    }
    
    fun ResponseRegisterCycleSchool.mapperToRegisterCycleSchool(): ModelRegisterCycleData {
        return ModelRegisterCycleData(
            teacherId = this.teacherId,
            schoolId = this.schoolId,
            name = this.name,
            description = this.description,
            isActive = this.isActive,
            idCycleSchool = this.idCycleSchool?:0
        )
    }
}