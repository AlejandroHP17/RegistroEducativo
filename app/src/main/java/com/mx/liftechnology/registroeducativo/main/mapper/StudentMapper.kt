package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.model.student.StudentDomainPar
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard

/**
 * Mapper para convertir modelos del dominio de estudiantes a modelos de UI.
 * Incluye validaciones para asegurar la integridad de los datos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object StudentMapper {
    
    /**
     * Convierte una lista de estudiantes del dominio a una lista de ModelCustomCard para la UI.
     * Valida que cada estudiante tenga datos válidos antes de mapearlo.
     * Los estudiantes se ordenan por apellido paterno, apellido materno y nombre.
     *
     * @param students La lista de estudiantes del dominio a convertir.
     * @return Una lista de ModelCustomCard ordenada y formateada para mostrar en la UI.
     */
    fun mapStudentListToCustomCard(students: List<StudentDomainPar>?): List<ModelCustomCard> {
        if (students == null) return emptyList()
        
        return students
            .filter { student ->
                // Validar que el estudiante tenga al menos un ID válido
                val hasValidId = student.studentId != null && student.studentId!! > 0
                // Validar que tenga al menos un nombre o apellido
                val hasValidName = !student.name.isNullOrBlank() || 
                                  !student.lastName.isNullOrBlank() || 
                                  !student.secondLastName.isNullOrBlank()
                hasValidId && hasValidName
            }
            .sortedWith(
                compareBy(
                    { it.lastName?.takeIf { it.isNotBlank() } ?: "" },
                    { it.secondLastName?.takeIf { it.isNotBlank() } ?: "" },
                    { it.name?.takeIf { it.isNotBlank() } ?: "" }
                )
            )
            .mapIndexed { index, student ->
                val fullName = buildString {
                    append(student.lastName?.takeIf { it.isNotBlank() } ?: "")
                    if (isNotEmpty()) append(" ")
                    append(student.secondLastName?.takeIf { it.isNotBlank() } ?: "")
                    if (isNotEmpty()) append(" ")
                    append(student.name?.takeIf { it.isNotBlank() } ?: "")
                }.trim()
                
                ModelCustomCard(
                    id = student.studentId ?: 0,
                    numberList = (index + 1).toString(),
                    nameCard = if (fullName.isNotBlank()) fullName else "Estudiante sin nombre"
                )
            }
    }
}

