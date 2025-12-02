package com.mx.liftechnology.domain.repository.schoolCycle.partial

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain

/**
 * Interfaz del repositorio para el registro de una lista de parciales.
 * Define el contrato para ejecutar la lógica de registro de una lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListPartialRepository{
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