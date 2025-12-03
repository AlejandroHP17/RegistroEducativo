/**
 * @file Define la interfaz del repositorio para obtener la lista de campos formativos con tipos de trabajo.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.repository.formativeFields

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain

/**
 * Interfaz del repositorio para obtener la lista de campos formativos con sus tipos de trabajo asociados.
 * Define el contrato para ejecutar la lógica de obtención de campos formativos agrupados por ciclo escolar.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListWotyFofiRepository {
    /**
     * Obtiene la lista de campos formativos con sus tipos de trabajo para un ciclo escolar específico.
     *
     * @param schoolCycleId El ID del ciclo escolar.
     * @return Un [ModelResult] que contiene los campos formativos con sus tipos de trabajo en caso de éxito,
     *         o un [NetworkModelError] en caso de fallo.
     */
    suspend fun getList(schoolCycleId: Int): ModelResult<WotyFofiDomain, NetworkModelError>
}
