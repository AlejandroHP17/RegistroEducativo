package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard

object StudentDomainToUIMapper {
    /**
     * Convierte una lista de estudiantes del dominio a una lista de ModelCustomCard para la UI.
     *
     * @param students La lista de estudiantes del dominio a convertir.
     * @return Una lista de ModelCustomCard ordenada y formateada para mostrar en la UI.
     */
    fun mapStudentListToCustomCard(students: List<ModelStudentDomain>?): List<ModelCustomCard> {
        return students?.sortedWith(
            compareBy(
                { it.lastName ?: "" },
                { it.secondLastName ?: "" },
                { it.name ?: "" }
            )
        )?.mapIndexed { index, student ->
            ModelCustomCard(
                id = student.studentId?:0,
                numberList = (index + 1).toString(),
                nameCard = "${student.lastName} ${student.secondLastName} ${student.name}".trim()
            )
        } ?: emptyList()
    }
}