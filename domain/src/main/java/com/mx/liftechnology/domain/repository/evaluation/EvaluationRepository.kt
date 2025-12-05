package com.mx.liftechnology.domain.repository.evaluation

import com.mx.liftechnology.core.network.api.RequestListGrades
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeEvaluationDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.ByFieldTypeStudentDomain
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain

/**
 * Interfaz del repositorio para operaciones relacionadas con evaluaciones.
 * Agrupa todas las operaciones relacionadas con evaluaciones y tipos de trabajo.
 *
 * @author Pelkidev
 * @version 2.0.0
 */
interface EvaluationRepository {
    /**
     * Registra evaluaciones de tipo de trabajo.
     *
     * @param formativeFieldId El ID del campo formativo.
     * @param partialId El ID del parcial.
     * @param workTypeId El ID del tipo de trabajo.
     * @param nameWork El nombre del trabajo.
     * @param workDate La fecha del trabajo.
     * @param schoolCycleId El ID del ciclo escolar.
     * @param grades La lista de calificaciones.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun registerWorkTypeEvaluations(
        formativeFieldId: Int,
        partialId: Int,
        workTypeId: Int,
        nameWork: String,
        workDate: String,
        schoolCycleId: Int,
        grades: List<RequestListGrades>
    ): ModelResult<WorkTypeEvaluationDomain, NetworkModelError>

    /**
     * Obtiene la lista de tipos de trabajo por campo formativo.
     *
     * @param formativeFieldId El ID del campo formativo.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getListWorkTypeStudent(formativeFieldId: Int): ModelResult<WorkTypeFormativeFieldDomain, NetworkModelError>

    /**
     * Obtiene la lista de evaluaciones de un estudiante.
     *
     * @param schoolCycleId El ID del ciclo escolar.
     * @param partialId El ID del parcial.
     * @param formativeFieldId El ID del campo formativo.
     * @param workTypeId El ID del tipo de trabajo.
     * @param studentId El ID del estudiante.
     * @param workDate La fecha del trabajo (opcional).
     * @param workDateFrom La fecha de inicio del rango (opcional).
     * @param workDateTo La fecha de fin del rango (opcional).
     * @return Un [ModelResult] que contiene la lista de evaluaciones del estudiante.
     */
    suspend fun getListEvaluations(
        schoolCycleId: Int,
        partialId: Int,
        formativeFieldId: Int,
        workTypeId: Int,
        studentId: Int,
        workDate: String?,
        workDateFrom: String?,
        workDateTo: String?
    ): ModelResult<List<EvaluationsStudentDomain>, NetworkModelError>

    /**
     * Obtiene la lista de estudiantes por tipo de campo formativo.
     *
     * @param formativeFieldId El ID del campo formativo.
     * @param workTypeId El ID del tipo de trabajo.
     * @param workName El nombre del trabajo (opcional).
     * @param workDate La fecha del trabajo (opcional).
     * @return Un [ModelResult] que contiene los datos de estudiantes por tipo de campo formativo.
     */
    suspend fun getByFieldType(
        formativeFieldId: Int,
        workTypeId: Int,
        workName: String?,
        workDate: String?
    ): ModelResult<ByFieldTypeStudentDomain, NetworkModelError>
}
