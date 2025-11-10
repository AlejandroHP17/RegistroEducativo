package com.mx.liftechnology.data.repository.flowMain.formativeFields

import com.mx.liftechnology.core.network.apiCall.flowMain.formativeField.DeleteFormativeFieldsApiCall
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
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
fun interface DeleteFormativeFieldsRepository{
    /**
     * Ejecuta la petición para obtener los datos de una escuela a partir de su CCT.
     *
     * @param studentId pertenece al estudiante.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeFormativeFieldsStudent(fieldId: Int): ModelResult<String, NetworkError>
}


class DeleteFormativeFieldsRepositoryImpl (
    private val deleteFormativeFieldsApiCall: DeleteFormativeFieldsApiCall
) : DeleteFormativeFieldsRepository {
    override suspend fun executeFormativeFieldsStudent(fieldId: Int): ModelResult<String, NetworkError> {
        return try {
            val response = deleteFormativeFieldsApiCall.callApi(fieldId)
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