package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.apiCall.formativeField.DeleteFormativeFieldApiCall
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de CCT.
 * Define el contrato para ejecutar la lógica de obtención de CCT.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface DeleteFormativeFieldRepository{
    /**
     * Ejecuta la petición para obtener los datos de una escuela a partir de su CCT.
     *
     * @param studentId pertenece al estudiante.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeFormativeFieldsStudent(fieldId: Int): ModelResult<String, NetworkModelError>
}


class DeleteFormativeFieldRepositoryImpl (
    private val deleteFormativeFieldApiCall: DeleteFormativeFieldApiCall
) : DeleteFormativeFieldRepository {
    override suspend fun executeFormativeFieldsStudent(fieldId: Int): ModelResult<String, NetworkModelError> {
        return try {
            val response = deleteFormativeFieldApiCall.callApi(fieldId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it)
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}