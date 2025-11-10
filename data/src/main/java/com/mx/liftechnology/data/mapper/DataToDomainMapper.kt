package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseEditStudent
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetStudent
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGroupTeacher
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponsePartials
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseRegisterCycleSchool
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseRegisterStudent
import com.mx.liftechnology.data.model.ModelCCTData
import com.mx.liftechnology.data.model.ModelCCTDataPeriodCatalog
import com.mx.liftechnology.data.model.ModelCycleSchoolData
import com.mx.liftechnology.data.model.ModelListPartialsData
import com.mx.liftechnology.data.model.ModelRegisterCycleData
import com.mx.liftechnology.data.model.ModelStudentData

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
    
    fun List<ResponsePartials?>.mapperToModelListPartialsData(): List<ModelListPartialsData>{
        return this.mapIndexedNotNull { index, partials ->
            ModelListPartialsData(
                description = partials?.description,
                startDate = partials?.startDate,
                endDate = partials?.endDate,
                partialId = partials?.partialId ?: 0
            )
        }
    }
    
    fun List<ResponseGetStudent?>.mapperToModelListStudent(): List<ModelStudentData>{
        return this.mapIndexedNotNull { index, student ->
            ModelStudentData(
                curp = student?.curp,
                name = student?.name,
                lastName = student?.lastName,
                secondLastName = student?.secondLastName,
                birthday = student?.birthday,
                phoneNumber = student?.phoneNumber,
                userId = student?.userId,
                studentId = student?.id,
                isActive = student?.isActive
            )
        }
    }

    fun ResponseRegisterStudent?.mapperToModelStudent(): ModelStudentData{
        return ModelStudentData(
                curp = this?.curp,
                name = this?.name,
                lastName = this?.lastName,
                secondLastName = this?.secondLastName,
                birthday = this?.birthday,
                phoneNumber = this?.phoneNumber,
                userId = this?.studentId,
                studentId = this?.studentId,
                isActive = this?.isActive
        )
    }

    fun ResponseEditStudent?.mapperToModelStudent(): ModelStudentData{
        return ModelStudentData(
                curp = this?.curp,
                name = this?.name,
                lastName = this?.lastName,
                secondLastName = this?.secondLastName,
                birthday = this?.birthday,
                phoneNumber = this?.phoneNumber,
                userId = this?.studentId,
                studentId = this?.studentId,
                isActive = this?.isActive
        )
    }
}