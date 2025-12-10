package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.domain.model.evaluation.CardDomain
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCardStudent

/**
 * Mapper para convertir modelos de UI de evaluaciones a modelos del dominio.
 * Incluye validaciones para asegurar la integridad de los datos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object EvaluationUIToDomainMapper {
    
    /**
     * Convierte una lista de ModelCustomCardStudent a una lista de CardDomain.
     * Valida que cada estudiante tenga datos válidos antes de mapearlo.
     *
     * @param students La lista de estudiantes de la UI a convertir.
     * @return Una lista de CardDomain con los datos validados.
     */
    fun List<CustomCardStudent>.toModelCard(): List<CardDomain> {
        return this.mapNotNull { student ->
            val studentId = student.id.toIntOrNull()
            val grade = student.score.valueText.toDoubleOrNull()
            
            // Validar que el ID del estudiante sea válido
            if (studentId == null || studentId <= 0) {
                null
            } else {
                CardDomain(
                    studentId = studentId,
                    grade = grade // Puede ser null si no hay calificación
                )
            }
        }
    }
}