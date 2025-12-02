package com.mx.liftechnology.domain.repository.formativeFields

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain

/**
 * Interfaz del repositorio para la obtención de tipos de evaluación.
 * Define el contrato para ejecutar la lógica de obtención de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetWorkTypeRepository{
    /**
     * Obtiene la lista de tipos de trabajo.
     *
     * @param teacherId El ID del profesor.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getList(teacherId: Int)
            : ModelResult<List<WorkTypeDomain>, NetworkModelError>
}