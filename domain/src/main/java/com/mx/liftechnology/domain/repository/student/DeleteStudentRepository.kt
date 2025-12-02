package com.mx.liftechnology.domain.repository.student

import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError

/**
 * Interfaz del repositorio para la eliminación de estudiantes.
 * Define el contrato para ejecutar la lógica de eliminación de un estudiante del sistema.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface DeleteStudentRepository {
    /**
     * Elimina un estudiante del sistema.
     *
     * @param studentId El ID del estudiante a eliminar.
     * @return Un [ModelResult] que contiene un mensaje de confirmación en caso de éxito,
     *         o un [NetworkModelError] en caso de fallo.
     */
    suspend fun delete(studentId: Int): ModelResult<String, NetworkModelError>
}