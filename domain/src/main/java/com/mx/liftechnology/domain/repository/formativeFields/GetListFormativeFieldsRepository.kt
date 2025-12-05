package com.mx.liftechnology.domain.repository.formativeFields

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain

/**
 * Interfaz del repositorio para la obtención de la lista de materias.
 * Define el contrato para ejecutar la lógica de obtención de la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListFormativeFieldRepository{
    /**
     * Obtiene la lista de materias formativas.
     *
     * @param cycleSchoolId El ID del ciclo escolar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getList(cycleSchoolId: Int)
            : ModelResult<List<FormativeFieldDomain>, NetworkModelError>
}