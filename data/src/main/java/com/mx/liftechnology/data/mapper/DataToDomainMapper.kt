package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGroupTeacher
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseRegisterCycleSchool
import com.mx.liftechnology.data.model.ModelCCTData
import com.mx.liftechnology.data.model.ModelCCTDataPeriodCatalog
import com.mx.liftechnology.data.model.ModelCycleSchoolData
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
    
    fun List<ResponseGroupTeacher>?.mapperToCycleSchool(): List<ModelCycleSchoolData> {
        return this?.mapIndexedNotNull { index, cycle ->
            ModelCycleSchoolData(
                teacherId = cycle.teacherId,
                schoolId = cycle.schoolId,
                name = cycle.name,
                grade = cycle.grade,
                group = cycle.groupName,
                isActive = cycle.isActive,
                cycleSchoolId = cycle.cycleSchoolId
            )
        }?:emptyList()
    }
}