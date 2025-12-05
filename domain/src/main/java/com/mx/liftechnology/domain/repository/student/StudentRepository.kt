package com.mx.liftechnology.domain.repository.student

import com.mx.liftechnology.core.network.api.RequestEditStudent
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain
import com.mx.liftechnology.domain.model.student.StudentDomain

/**
 * Interfaz del repositorio para operaciones relacionadas con estudiantes.
 * Agrupa todas las operaciones CRUD y consultas relacionadas con estudiantes.
 *
 * @author Pelkidev
 * @version 2.0.0
 */
interface StudentRepository {
    /**
     * Obtiene la lista de estudiantes.
     *
     * @param cycleSchoolId El ID del ciclo escolar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getStudents(cycleSchoolId: Int): ModelResult<List<StudentDomain>, NetworkModelError>

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
        teacherId: Int,
        schoolCycleId: Int,
        isActive: Boolean
    ): ModelResult<StudentDomain?, NetworkModelError>

    /**
     * Edita un estudiante existente.
     *
     * @param request Los datos de la petición de edición.
     * @param studentId El ID del estudiante a editar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun edit(
        request: RequestEditStudent,
        studentId: Int
    ): ModelResult<StudentDomain, NetworkModelError>

    /**
     * Elimina un estudiante del sistema.
     *
     * @param studentId El ID del estudiante a eliminar.
     * @return Un [ModelResult] que contiene un mensaje de confirmación en caso de éxito,
     *         o un [NetworkModelError] en caso de fallo.
     */
    suspend fun delete(studentId: Int): ModelResult<String, NetworkModelError>
}
