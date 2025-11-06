package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatAssignment
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelSubComplexCard

/**
 * Mapper centralizado para convertir modelos del dominio a modelos de UI.
 * Este mapper mantiene la separación de capas, centralizando toda la lógica de transformación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object DomainToUIMapper {

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
                id = student.studentId ?: "",
                numberList = (index + 1).toString(),
                nameCard = "${student.lastName} ${student.secondLastName} ${student.name}".trim()
            )
        } ?: emptyList()
    }

    /**
     * Convierte una lista de materias del dominio a una lista de ModelCustomCard para la UI.
     *
     * @param subjects La lista de materias del dominio a convertir.
     * @return Una lista de ModelCustomCard formateada para mostrar en la UI.
     */
    fun mapSubjectListToCustomCard(subjects: List<ModelFormatSubjectDomain>?): List<ModelCustomCard> {
        return subjects?.map {
            ModelCustomCard(
                id = it.subjectId.toString(),
                numberList = "",
                nameCard = "${it.name}"
            )
        } ?: emptyList()
    }

    /**
     * Convierte un ModelFormatSubjectDomain a un ModelComplexCard para la UI.
     *
     * @param subject La materia del dominio a convertir.
     * @return Un ModelComplexCard con la información de la materia.
     */
    fun mapSubjectToComplexCard(subject: ModelFormatSubjectDomain?): ModelComplexCard {
        return ModelComplexCard(
            idTitle = subject?.subjectId,
            nameTitle = subject?.name,
            isShowTitle = true,
            isExpandedTitle = true,
            list = null,
        )
    }

    /**
     * Convierte una lista de asignaciones del dominio a una lista de ModelSubComplexCard para la UI.
     *
     * @param assignments La lista de asignaciones del dominio a convertir.
     * @return Una lista de ModelSubComplexCard formateada para mostrar en la UI.
     */
    fun mapAssignmentListToSubComplexCard(assignments: List<ModelFormatAssignment>?): List<ModelSubComplexCard?>? {
        return assignments?.map {
            ModelSubComplexCard(
                idSubTitle = it.id,
                nameSubTitle = it.assignmentName.valueText,
                isShowSubTitle = true,
                isExpandedSubTitle = false,
                list = null,
            )
        }
    }
}

