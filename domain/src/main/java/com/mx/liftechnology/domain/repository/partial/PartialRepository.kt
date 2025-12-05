package com.mx.liftechnology.domain.repository.partial

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain

/**
 * Interfaz del repositorio para operaciones relacionadas con parciales.
 * Agrupa todas las operaciones relacionadas con parciales.
 *
 * @author Pelkidev
 * @version 2.0.0
 */
interface PartialRepository {
    /**
     * Obtiene la lista de parciales.
     *
     * @param schoolCycleId El ID del ciclo escolar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getList(schoolCycleId: Int): ModelResult<List<ListPartialDomain>, NetworkModelError>

    /**
     * Registra una lista de parciales.
     *
     * @param adapterPeriods La lista de períodos a registrar.
     * @param cycleSchoolId El ID del ciclo escolar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun register(
        adapterPeriods: List<DatePeriodDomain>,
        cycleSchoolId: Int
    ): ModelResult<List<ListPartialDomain?>, NetworkModelError>
}
