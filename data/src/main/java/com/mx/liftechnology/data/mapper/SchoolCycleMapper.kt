package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseGroupTeacher
import com.mx.liftechnology.core.network.api.ResponseRegisterSchoolCycle
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain
import com.mx.liftechnology.domain.model.schoolCycle.SchoolCycleDomain

object SchoolCycleMapper {


    /**
     * Convierte una lista de [ResponseGroupTeacher] a una lista de [SchoolCycleDomain] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener ciclos escolares.
     * @return Una lista de objetos [SchoolCycleDomain] con los datos mapeados. Los elementos nulos son omitidos.
     */
    fun List<ResponseGroupTeacher>.toSchoolCycleDomain(): List<SchoolCycleDomain> {
        return this.map { cycle ->
            cycle.let {
                SchoolCycleDomain(
                    teacherId = it.teacherId,
                    schoolId = it.schoolId,
                    name = it.name,
                    grade = it.grade,
                    group = it.groupName,
                    isActive = it.isActive,
                    cycleSchoolId = it.schoolCycleId
                )
            }
        }
    }


    /**
     * Convierte un [ResponseRegisterSchoolCycle] a [RegisterSchoolCycleDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para registrar ciclo escolar.
     * @return Un objeto [RegisterSchoolCycleDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseRegisterSchoolCycle.toRegisterSchoolCycleDomain(): RegisterSchoolCycleDomain {
        return RegisterSchoolCycleDomain(
            teacherId = teacherId,
            schoolId = schoolId,
            name = name,
            isActive = isActive,
            idCycleSchool = schoolCycleId
        )
    }
}