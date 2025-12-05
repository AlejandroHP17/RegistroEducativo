package com.mx.liftechnology.domain.repository.workType

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain

/**
 * Interfaz del repositorio para operaciones relacionadas con campos formativos.
 * Agrupa todas las operaciones CRUD y consultas relacionadas con campos formativos.
 *
 * @author Pelkidev
 * @version 2.0.0
 */
interface WorkTypeRepository {

    /**
     * Obtiene los tipos de trabajo asociados a un campo formativo específico.
     *
     * @param formativeFieldId El ID del campo formativo.
     * @return Un [ModelResult] que contiene los tipos de trabajo asociados al campo formativo.
     */
    suspend fun getWorkTypeByFormativeField(formativeFieldId: Int): ModelResult<WorkTypeByFormativeFieldDomain, NetworkModelError>

    /**
     * Obtiene la lista de tipos de trabajo.
     *
     * @param teacherId El ID del profesor.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getWorkTypeList(teacherId: Int): ModelResult<List<WorkTypeDomain>, NetworkModelError>
}