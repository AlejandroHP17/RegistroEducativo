/**
 * @file Define el repositorio para la funcionalidad de registro de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.apiCall.student.RegisterStudentApiCall
import com.mx.liftechnology.core.network.apiCall.student.RequestRegisterStudent
import com.mx.liftechnology.data.mapper.StudentDataToDomainMapper.mapperToModelStudent
import com.mx.liftechnology.data.model.student.ModelStudentData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de estudiantes.
 * Define el contrato para ejecutar la lógica de registro de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterStudentRepository{
    /**
     * Ejecuta la petición de registro de un estudiante.
     *
     * @param request Los datos de la petición de registro.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeRegisterStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String,
        teacherId : Int,
        schoolCycleId : Int,
        isActive : Boolean
    )
    : ModelResult<ModelStudentData?, NetworkModelError>
}

/**
 * Implementación de [RegisterStudentRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerStudentApiCall La llamada a la API para el registro de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterStudentRepositoryImpl(
    private val registerStudentApiCall: RegisterStudentApiCall,
) : RegisterStudentRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String,
        teacherId : Int,
        schoolCycleId : Int,
        isActive : Boolean
    ): ModelResult<ModelStudentData?, NetworkModelError> {
        val request = RequestRegisterStudent(
            name = name,
            lastName = lastName,
            secondLastName = secondLastName,
            curp = curp,
            birthday = birthday,
            phoneNumber = phoneNumber,
            teacherId = teacherId,
            schoolCycleId = schoolCycleId,
            isActive = true
        )
        return try {
            val response = registerStudentApiCall.callApi(request)
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