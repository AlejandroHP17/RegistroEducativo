package com.mx.liftechnology.domain.repository.schoolCycle.partial

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain

/**
 * Interfaz del repositorio para la obtención de la lista de parciales.
 * Define el contrato para ejecutar la lógica de obtención de la lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListPartialRepository{
    /**
     * Obtiene la lista de parciales.
     *
     * @param schoolCycleId El ID del ciclo escolar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getList(
        schoolCycleId : Int
    ): ModelResult<List<ListPartialDomain>, NetworkModelError>
}