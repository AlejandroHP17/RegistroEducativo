package com.mx.liftechnology.domain.repository.school

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain

/**
 * Interfaz del repositorio para el registro de escuelas.
 * Define el contrato para ejecutar la lógica de registro de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterCycleSchoolRepository{
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
    suspend fun register(
        teacherId : Int,
        schoolId : Int,
        name : String,
        cycleLabel : String,
        grade : String,
        nameGroup : String,
        periodCatalogId : Int
    ): ModelResult<RegisterSchoolCycleDomain, NetworkModelError>
}