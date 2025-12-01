/**
 * @file Define el repositorio para la eliminación de campos formativos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

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

/**
 * Implementación de [DeleteFormativeFieldRepository].
 * Se encarga de realizar la llamada a la API para eliminar un campo formativo y gestionar las respuestas.
 *
 * @property formativeFieldApi La API de campos formativos para realizar la operación de eliminación.
 * @author Pelkidev
 * @version 1.0.0
 */
class DeleteFormativeFieldRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
) : DeleteFormativeFieldRepository {
    /**
     * {@inheritDoc}
     */
    override suspend fun delete(fieldId: Int): ModelResult<String, NetworkModelError> {
        return formativeFieldApi.deleteFormativeField(fieldId).executeOrError { it }
    }
}