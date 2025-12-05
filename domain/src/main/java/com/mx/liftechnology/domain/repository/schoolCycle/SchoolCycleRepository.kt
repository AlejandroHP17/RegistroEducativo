package com.mx.liftechnology.domain.repository.schoolCycle

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain
import com.mx.liftechnology.domain.model.schoolCycle.SchoolCycleDomain

/**
 * Interfaz del repositorio para operaciones relacionadas con escuelas y ciclos escolares.
 * Agrupa todas las operaciones relacionadas con escuelas y ciclos escolares.
 *
 * @author Pelkidev
 * @version 2.0.0
 */
interface SchoolCycleRepository {

    /**
     * Obtiene la lista de ciclos escolares.
     *
     * @param teacherId El ID del profesor.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getCycleSchool(
        teacherId: Int
    ): ModelResult<List<SchoolCycleDomain>, NetworkModelError>

    /**
     * Registra un ciclo escolar.
     *
     * @param teacherId El ID del profesor.
     * @param schoolId El ID de la escuela.
     * @param name El nombre del ciclo.
     * @param cycleLabel La etiqueta del ciclo.
     * @param grade El grado.
     * @param nameGroup El nombre del grupo.
     * @param periodCatalogId El ID del catálogo de períodos.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun registerCycleSchool(
        teacherId: Int,
        schoolId: Int,
        name: String,
        cycleLabel: String,
        grade: String,
        nameGroup: String,
        periodCatalogId: Int
    ): ModelResult<RegisterSchoolCycleDomain, NetworkModelError>
}