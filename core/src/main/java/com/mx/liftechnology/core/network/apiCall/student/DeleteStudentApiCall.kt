package com.mx.liftechnology.core.network.apiCall.student

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.environment.Environment
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

/**
 * Interfaz para la llamada a la API de eliminar estudiante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface DeleteStudentApiCall {

    @DELETE(Environment.END_POINT_DELETE_STUDENT)
    suspend fun callApi(
        @Path("student_id") studentId: Int
    ): Response<ResponseGeneric<String>>
}