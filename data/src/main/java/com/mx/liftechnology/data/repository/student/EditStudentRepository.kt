package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.api.RequestEditStudent
import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.mapper.StudentDataToDomainMapper.mapperToModelStudent
import com.mx.liftechnology.data.model.student.ModelStudentData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
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
            : ModelResult<ModelStudentData, NetworkModelError>
}

class EditStudentRepositoryImpl (
    private val studentApi: StudentApi
) : EditStudentRepository {
    override suspend fun executeEditStudent(request: RequestEditStudent, studentId: Int)
    : ModelResult<ModelStudentData, NetworkModelError> {
        return try {
            val response = studentApi.editStudent(studentId, request)
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