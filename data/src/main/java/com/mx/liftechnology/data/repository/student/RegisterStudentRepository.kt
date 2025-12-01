/**
 * @file Define el repositorio para la funcionalidad de registro de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.student

import com.mx.liftechnology.core.network.api.RequestRegisterStudent
import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.data.mapper.StudentMapper.toData
import com.mx.liftechnology.data.model.student.StudentData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para el registro de estudiantes.
 * Define el contrato para ejecutar la lógica de registro de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterStudentRepository{
    /**
     * Registra un nuevo estudiante.
     *
     * @param name El nombre del estudiante.
     * @param lastName El apellido paterno del estudiante.
     * @param secondLastName El apellido materno del estudiante.
     * @param curp La CURP del estudiante.
     * @param birthday La fecha de nacimiento del estudiante.
     * @param phoneNumber El número de teléfono del estudiante.
     * @param teacherId El ID del profesor.
     * @param schoolCycleId El ID del ciclo escolar.
     * @param isActive Indica si el estudiante está activo.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun register(
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
    : ModelResult<StudentData?, NetworkModelError>
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
    private val studentApi: StudentApi,
) : RegisterStudentRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun register(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String,
        teacherId : Int,
        schoolCycleId : Int,
        isActive : Boolean
    ): ModelResult<StudentData?, NetworkModelError> {
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
        return studentApi.registerStudent(request).executeOrError { it.toData() }
    }
}