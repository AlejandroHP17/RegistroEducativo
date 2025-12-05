package com.mx.liftechnology.domain.usecase.workType

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository
import com.mx.liftechnology.domain.repository.student.StudentRepository

/**
 * Caso de uso para obtener la lista de evaluaciones de un estudiante.
 * Encapsula la lógica de negocio para recuperar las evaluaciones de un estudiante específico,
 * permitiendo filtrar por tipo de trabajo, campo formativo y rango de fechas.
 *
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 * @property studentRepository El repositorio para operaciones relacionadas con estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListEvaluationsStudentUseCase(
    private val preference: PreferenceUseCase,
    private val studentRepository: EvaluationRepository,
) {
    /**
     * Ejecuta el proceso de obtención de la lista de evaluaciones de un estudiante.
     * Obtiene los IDs necesarios desde las preferencias (ciclo escolar y parcial) y recupera
     * las evaluaciones del estudiante con los filtros proporcionados.
     *
     * @param workTypeId El identificador del tipo de trabajo. Requerido.
     * @param studentId El identificador único del estudiante. Requerido.
     * @param formativeFieldId El identificador del campo formativo. Requerido.
     * @param workDate La fecha específica de trabajo para filtrar (formato de fecha). Opcional.
     * @param workDateFrom La fecha de inicio del rango para filtrar evaluaciones. Opcional.
     * @param workDateTo La fecha de fin del rango para filtrar evaluaciones. Opcional.
     * @return Un [ModelResult] que contiene la lista de evaluaciones del estudiante
     * ([List<ModelEvaluationsStudent>]) en caso de éxito, o un estado de error específico en caso de fallo.
     *
     * Posibles errores:
     * - [LocalModelError.USER_INCOMPLETE_DATA] si faltan datos necesarios (schoolCycleId, partialId, studentId, workTypeId o formativeFieldId)
     * - [ModelError] de red si hay problemas de conexión
     * - [ModelError] si no se encuentran evaluaciones con los criterios proporcionados
     *
     * @example
     * ```
     * // Obtener todas las evaluaciones de un estudiante
     * val result = getListEvaluationsStudentUseCase(
     *     workTypeId = 1,
     *     studentId = 123,
     *     formativeFieldId = 5
     * )
     *
     * // Obtener evaluaciones en un rango de fechas
     * val resultWithDates = getListEvaluationsStudentUseCase(
     *     workTypeId = 1,
     *     studentId = 123,
     *     formativeFieldId = 5,
     *     workDateFrom = "2024-01-01",
     *     workDateTo = "2024-12-31"
     * )
     *
     * when (result) {
     *     is SuccessResult -> println("Evaluaciones encontradas: ${result.data.size}")
     *     is ErrorResult -> println("Error: ${result.error}")
     * }
     * ```
     */
    suspend operator fun invoke(
        workTypeId: Int?,
        studentId: Int?,
        formativeFieldId : Int?,
        workDate : String? = null,
        workDateFrom : String? = null,
        workDateTo : String? = null

    ): ModelResult<List<EvaluationsStudentDomain>, ModelError> {
        val schoolCycleId = preference.getIdCycleSchool()
        val partialId = preference.getIdPartial()

        if (schoolCycleId == null || partialId == null || studentId == null || workTypeId== null || formativeFieldId == null) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        return studentRepository.getListEvaluations(
            schoolCycleId = schoolCycleId,
            partialId = partialId,
            formativeFieldId = formativeFieldId,
            workTypeId = workTypeId,
            studentId = studentId,
            workDate = workDate,
            workDateFrom = workDateFrom,
            workDateTo = workDateTo
        )

    }
}