package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.core.network.api.RequestEditStudent
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.model.student.toStudentDomain
import com.mx.liftechnology.domain.repository.student.EditStudentRepository

/**
 * Caso de uso para editar la información de un estudiante existente.
 * Encapsula la lógica de negocio para actualizar los datos de un estudiante mediante su identificador.
 *
 * @property editStudentRepository El repositorio para las operaciones de edición de estudiantes.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class EditStudentUseCase (
    private val editStudentRepository: EditStudentRepository,
    private val preference: PreferenceUseCase
){
    /**
     * Ejecuta el proceso de edición de un estudiante.
     * Valida que existan los datos necesarios (ID de profesor, ciclo escolar e ID del estudiante)
     * y actualiza la información del estudiante en el sistema.
     *
     * @param name El nombre del estudiante.
     * @param lastName El apellido paterno del estudiante.
     * @param secondLastName El apellido materno del estudiante.
     * @param curp La CURP del estudiante.
     * @param birthday La fecha de nacimiento del estudiante.
     * @param phoneNumber El número de teléfono del estudiante.
     * @param studentId El identificador único del estudiante a editar.
     * @return Un [ModelResult] que contiene los datos actualizados del estudiante ([StudentDomain]) en caso de éxito,
     * o un estado de error específico en caso de fallo.
     *
     * Posibles errores:
     * - [LocalModelError.USER_INCOMPLETE_DATA] si faltan datos necesarios (teacherId, cycleSchoolId o studentId)
     * - [ModelError] de red si hay problemas de conexión
     * - [ModelError] de validación si los datos proporcionados no son válidos
     *
     * @example
     * ```
     * val result = editStudentUseCase(
     *     name = "Juan",
     *     lastName = "Pérez",
     *     secondLastName = "García",
     *     curp = "PEGJ950101HDFRRN01",
     *     birthday = "1995-01-01",
     *     phoneNumber = "5551234567",
     *     studentId = 123
     * )
     * when (result) {
     *     is SuccessResult -> println("Estudiante actualizado: ${result.data?.name}")
     *     is ErrorResult -> println("Error: ${result.error}")
     * }
     * ```
     */
    suspend operator fun invoke(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String,
        studentId: Int?
    ): ModelResult<StudentDomain?, ModelError> {
        val teacherId= preference.getIdUser()
        val cycleSchoolId = preference.getIdCycleSchool()

        if(teacherId == null || cycleSchoolId == null || studentId == null ) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        val request = RequestEditStudent(
            name = name,
            lastName = lastName,
            secondLastName = secondLastName,
            curp = curp,
            birthday = birthday,
            phoneNumber = phoneNumber,
            teacherId = teacherId,
            cycleSchoolId = cycleSchoolId,
            isActive = true
        )

        val result = editStudentRepository.edit(request, studentId)
        return when (result) {
            is SuccessResult -> {
                SuccessResult(result.data.toStudentDomain())
            }
            is ErrorResult -> result
        }
    }
}