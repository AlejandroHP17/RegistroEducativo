package com.mx.liftechnology.domain.model.formativeFields

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Modelo de datos que representa el formato de una asignación o trabajo en la capa de dominio.
 * Contiene toda la información necesaria para gestionar una asignación, incluyendo su ponderación y descripción.
 *
 * @property id El identificador único del formato de la asignación.
 * @property percent El valor porcentual de la asignación sobre la calificación final.
 * @property subjectSchoolCycleGroupId El ID que relaciona la materia con el grupo y ciclo escolar.
 * @property description El estado del campo de texto para la descripción de la asignación.
 * @property teacherSchoolCycleGroupId El ID que relaciona al profesor con el grupo y ciclo escolar.
 * @property assignmentId El ID único de la asignación.
 * @property assignmentName El estado del campo de texto para el nombre de la asignación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelFormatAssignment (
    val id :Int?,
    val percent:Int?,
    val subjectSchoolCycleGroupId :	Int?,
    val description	: ModelStateOutFieldText,
    val teacherSchoolCycleGroupId: Int?,
    val assignmentId: Int?,
    val assignmentName:ModelStateOutFieldText
)
