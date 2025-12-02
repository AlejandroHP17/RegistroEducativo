package com.mx.liftechnology.domain.model.student

/**
 * @file Define el modelo de datos para una evaluación de estudiante.
 * @author Pelkidev
 * @version 1.0.0
 */
/**
 * Modelo de datos que representa una evaluación realizada a un estudiante.
 * Contiene la información de una evaluación específica, incluyendo la calificación obtenida.
 *
 * **Propósito:**
 * Este modelo almacena los datos de una evaluación de un estudiante, incluyendo
 * el nombre de la evaluación, la calificación y la fecha.
 *
 * **Uso:**
 * Este modelo se utiliza en:
 * - [GetListEvaluationsStudentRepository] para almacenar las evaluaciones recibidas de la API
 * - Mappers para convertir entre modelos de red y modelos de dominio
 * - Casos de uso para mostrar el historial de evaluaciones de un estudiante
 *
 * @property studentId El ID del estudiante al que pertenece esta evaluación. Campo requerido.
 * @property evaluationName El nombre de la evaluación (ej: "Examen Parcial 1", "Tarea 3"). Campo requerido.
 * @property grade La calificación obtenida por el estudiante en esta evaluación. Puede ser nulo si aún no se ha calificado.
 * @property workDate La fecha en que se realizó o entregó la evaluación en formato String.
 * @property evaluationId El ID único de la evaluación. Campo requerido.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class EvaluationsStudentDomain(
    val studentId: Int,
    val evaluationName: String,
    val grade: Double?,
    val workDate: String?,
    val evaluationId: Int,
)