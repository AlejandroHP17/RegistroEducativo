package com.mx.liftechnology.domain.repository.schoolCycle.menu

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.schoolCycle.SchoolCycleDomain

/**
 * Interfaz del repositorio para el menú principal.
 * Define el contrato para obtener la lista de grupos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface MenuRepository{
    /**
     * Obtiene la lista de ciclos escolares.
     *
     * @param teacherId El ID del profesor.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getCycleSchool(
        teacherId: Int
    ): ModelResult<List<SchoolCycleDomain>, NetworkModelError>
}