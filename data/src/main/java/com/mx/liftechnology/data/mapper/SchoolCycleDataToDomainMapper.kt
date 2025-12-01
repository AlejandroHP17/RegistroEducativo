package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseCctSchool
import com.mx.liftechnology.core.network.api.ResponseGetPartials
import com.mx.liftechnology.core.network.api.ResponseGroupTeacher
import com.mx.liftechnology.core.network.api.ResponseRegisterPartial
import com.mx.liftechnology.core.network.api.ResponseRegisterSchoolCycle
import com.mx.liftechnology.data.model.schoolCycle.ModelCCTData
import com.mx.liftechnology.data.model.schoolCycle.ModelCCTDataPeriodCatalog
import com.mx.liftechnology.data.model.schoolCycle.ModelListPartialData
import com.mx.liftechnology.data.model.schoolCycle.ModelRegisterSchoolCycleData
import com.mx.liftechnology.data.model.schoolCycle.ModelSchoolCycleData

object SchoolCycleDataToDomainMapper {

    fun List<ResponseGroupTeacher>?.mapperToCycleSchool(): List<ModelSchoolCycleData> {
        return this?.mapIndexed { index, cycle ->
            ModelSchoolCycleData(
                teacherId = cycle.teacherId,
                schoolId = cycle.schoolId,
                name = cycle.name,
                grade = cycle.grade,
                group = cycle.groupName,
                isActive = cycle.isActive,
                cycleSchoolId = cycle.schoolCycleId
            )
        }?:emptyList()
    }


    fun List<ResponseGetPartials>.mapperToModelListPartialsData(): List<ModelListPartialData>{
        return this.mapIndexed { index, partials ->
            ModelListPartialData(
                description = partials.description,
                startDate = partials.startDate,
                endDate = partials.endDate,
                partialId = partials.partialId
            )
        }
    }

    fun List<ResponseRegisterPartial>.mapperToModelListPartialData(): List<ModelListPartialData>{
        return this.mapIndexed { index, partials ->
            ModelListPartialData(
                description = partials.description,
                startDate = partials.startDate,
                endDate = partials.endDate,
                partialId = partials.partialId
            )
        }
    }

    fun ResponseCctSchool.mapperToRegisterSchool(): ModelCCTData {
        return ModelCCTData(
            id = this.schoolId,
            cct = this.cct,
            schoolTypeId = this.schoolTypeId,
            schoolName = this.schoolName,
            shiftName = this.shiftName,
            periodCatalog = this.periodCatalog?.map { catalog ->
                ModelCCTDataPeriodCatalog(
                    id = catalog.id,
                    typeName = catalog.typeName,
                    periodNumber = catalog.periodNumber
                )
            } ?: emptyList()
        )
    }

    fun ResponseRegisterSchoolCycle.mapperToRegisterCycleSchool(): ModelRegisterSchoolCycleData {
        return ModelRegisterSchoolCycleData(
            teacherId = this.teacherId,
            schoolId = this.schoolId,
            name = this.name,
            isActive = this.isActive,
            idCycleSchool = this.schoolCycleId
        )
    }
}