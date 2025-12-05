/**
 * @file Define el repositorio para la eliminación de campos formativos.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.repository.formativeFields.DeleteFormativeFieldRepository

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
        return safeApiCall(
            apiCall = { formativeFieldApi.deleteFormativeField(fieldId) },
            mapper = { it.toDomain() }
        )
    }
}