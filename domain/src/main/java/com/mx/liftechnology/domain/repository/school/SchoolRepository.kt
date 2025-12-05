package com.mx.liftechnology.domain.repository.school

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.schoolCycle.CCTDomain
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain

/**
 * Interfaz del repositorio para operaciones relacionadas con escuelas y ciclos escolares.
 * Agrupa todas las operaciones relacionadas con escuelas y ciclos escolares.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface SchoolRepository {
    /**
     * Obtiene los datos de una escuela a partir de su CCT.
     *
     * @param cct El CCT de la escuela.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getCct(cct: String): ModelResult<CCTDomain, NetworkModelError>


}
