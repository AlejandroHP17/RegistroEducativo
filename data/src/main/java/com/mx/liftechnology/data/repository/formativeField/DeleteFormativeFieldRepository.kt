package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para la obtención de CCT.
 * Define el contrato para ejecutar la lógica de obtención de CCT.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface DeleteFormativeFieldRepository{
    /**
     * Elimina un campo formativo.
     *
     * @param fieldId El ID del campo formativo a eliminar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun delete(fieldId: Int): ModelResult<String, NetworkModelError>
}


class DeleteFormativeFieldRepositoryImpl (
    private val formativeFieldApi: FormativeFieldApi
) : DeleteFormativeFieldRepository {
    override suspend fun delete(fieldId: Int): ModelResult<String, NetworkModelError> {
        return formativeFieldApi.deleteFormativeField(fieldId).executeOrError { it }
    }
}