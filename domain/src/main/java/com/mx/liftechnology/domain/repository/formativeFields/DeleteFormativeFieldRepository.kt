package com.mx.liftechnology.domain.repository.formativeFields

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError


/**
 * Interfaz del repositorio para la eliminación de campos formativos.
 * Define el contrato para ejecutar la lógica de eliminación de un campo formativo del sistema.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface DeleteFormativeFieldRepository {
    /**
     * Elimina un campo formativo del sistema.
     *
     * @param fieldId El ID del campo formativo a eliminar.
     * @return Un [ModelResult] que contiene un mensaje de confirmación en caso de éxito,
     *         o un [NetworkModelError] en caso de fallo.
     */
    suspend fun delete(fieldId: Int): ModelResult<String, NetworkModelError>
}