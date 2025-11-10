package com.mx.liftechnology.data.repository.flowMain.student

import com.mx.liftechnology.core.network.apiCall.flowMain.EditStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestEditStudent
import com.mx.liftechnology.data.mapper.DataToDomainMapper.mapperToModelStudent
import com.mx.liftechnology.data.model.ModelStudentData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

fun interface EditStudentRepository{
    /**
     * Ejecuta la petición de registro de un estudiante.
     *
     * @param request Los datos de la petición de registro.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeEditStudent(request: RequestEditStudent, studentId: Int)
            : ModelResult<ModelStudentData?, NetworkError>
}

class EditStudentRepositoryImpl (
    private val editStudentApiCall: EditStudentApiCall
) : EditStudentRepository {
    override suspend fun executeEditStudent(request: RequestEditStudent, studentId: Int)
    : ModelResult<ModelStudentData?, NetworkError> {
        return try {
            val response = editStudentApiCall.callApi(studentId, request )
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelStudent())
                } ?: ErrorResult(NetworkException.handleException(NullPointerException()))
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}