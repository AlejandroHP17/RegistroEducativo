package com.mx.liftechnology.domain.repository.student

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.student.StudentDomain

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
    ): ModelResult<StudentDomain?, NetworkModelError>
}