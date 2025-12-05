package com.mx.liftechnology.domain.repository.student

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.student.StudentDomain


/**
 * Interfaz del repositorio para la obtención de la lista de estudiantes.
 * Define el contrato para ejecutar la lógica de obtención de la lista de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetStudentRepository{
    /**
     * Obtiene la lista de estudiantes.
     *
     * @param cycleSchoolId El ID del ciclo escolar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getStudents(cycleSchoolId: Int): ModelResult<List<StudentDomain>, NetworkModelError>
}