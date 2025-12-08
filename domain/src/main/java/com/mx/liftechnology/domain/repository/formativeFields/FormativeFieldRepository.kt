package com.mx.liftechnology.domain.repository.formativeFields

import com.mx.liftechnology.core.network.api.RequestEvaluations
import com.mx.liftechnology.core.network.api.RequestWorkType
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain

/**
 * Interfaz del repositorio para operaciones relacionadas con campos formativos.
 * Agrupa todas las operaciones CRUD y consultas relacionadas con campos formativos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface FormativeFieldRepository {
    /**
     * Obtiene la lista de materias formativas.
     *
     * @param cycleSchoolId El ID del ciclo escolar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getList(cycleSchoolId: Int): ModelResult<List<FormativeFieldDomain>, NetworkModelError>

    /**
     * Obtiene la lista de campos formativos con sus tipos de trabajo para un ciclo escolar específico.
     *
     * @param schoolCycleId El ID del ciclo escolar.
     * @return Un [ModelResult] que contiene los campos formativos con sus tipos de trabajo en caso de éxito,
     *         o un [NetworkModelError] en caso de fallo.
     */
    suspend fun getListWotyFofi(schoolCycleId: Int): ModelResult<WotyFofiDomain, NetworkModelError>

    /**
     * Registra una materia formativa en bulk.
     *
     * @param cycleSchoolId El ID del ciclo escolar.
     * @param formativeFieldName El nombre de la materia formativa.
     * @param code El código de la materia.
     * @param workTypes La lista de tipos de trabajo.
     * @param evaluations La lista de evaluaciones.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun registerBulk(
        cycleSchoolId: Int,
        formativeFieldName: String,
        code: String,
        workTypes: List<RequestWorkType>,
        evaluations: List<RequestEvaluations>
    ): ModelResult<FormativeFieldDomain, NetworkModelError>

    /**
     * Elimina un campo formativo del sistema.
     *
     * @param fieldId El ID del campo formativo a eliminar.
     * @return Un [ModelResult] que contiene un mensaje de confirmación en caso de éxito,
     *         o un [NetworkModelError] en caso de fallo.
     */
    suspend fun delete(fieldId: Int): ModelResult<String, NetworkModelError>
}
