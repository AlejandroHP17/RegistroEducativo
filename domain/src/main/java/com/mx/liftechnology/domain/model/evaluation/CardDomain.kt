/**
 * @file Define el modelo de dominio para una tarjeta de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.evaluation

/**
 * Modelo de datos que representa una tarjeta de evaluación en la capa de dominio.
 * Contiene la información básica de una evaluación de un estudiante.
 *
 * @property studentId El ID del estudiante al que pertenece esta evaluación.
 * @property grade La calificación obtenida por el estudiante. Puede ser nulo si aún no se ha calificado.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class CardDomain(
    val studentId: Int,
    val grade: Double?
)
