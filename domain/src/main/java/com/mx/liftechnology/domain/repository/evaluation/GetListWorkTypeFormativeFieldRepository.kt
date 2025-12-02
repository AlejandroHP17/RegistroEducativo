package com.mx.liftechnology.domain.repository.evaluation

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain

/**
 * Interfaz del repositorio para la obtención de la lista de tipos de evaluación.
 * Define el contrato para ejecutar la lógica de obtención de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListWorkTypeFormativeFieldRepository {
    /**
     * Obtiene la lista de tipos de trabajo por campo formativo.
     *
     * @param formativeFieldId El ID del campo formativo.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getList(formativeFieldId:Int) : ModelResult<WorkTypeFormativeFieldDomain, NetworkModelError>
}