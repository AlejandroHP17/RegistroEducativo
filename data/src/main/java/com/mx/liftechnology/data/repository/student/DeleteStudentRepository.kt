package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.apiCall.student.DeleteStudentApiCall
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
fun interface DeleteStudentRepository{
    /**
     * Ejecuta la petición para obtener los datos de una escuela a partir de su CCT.
     *
     * @param studentId pertenece al estudiante.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeDeleteStudent(studentId: Int): ModelResult<String, NetworkError>
}


class DeleteStudentRepositoryImpl (
    private val deleteStudentApiCall: DeleteStudentApiCall
) : DeleteStudentRepository {
    override suspend fun executeDeleteStudent(studentId: Int): ModelResult<String, NetworkError> {
        return try {
            val response = deleteStudentApiCall.callApi(studentId)
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