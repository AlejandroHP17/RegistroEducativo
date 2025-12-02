package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.domain.repository.student.DeleteStudentRepository

/**
 * Caso de uso para eliminar un estudiante del sistema.
 * Encapsula la lógica de negocio para eliminar un estudiante mediante su identificador.
 *
 * @property deleteStudentRepository El repositorio para las operaciones de eliminación de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class DeleteStudentUseCase(
    private val deleteStudentRepository: DeleteStudentRepository
) {

    /**
     * Ejecuta el proceso de eliminación de un estudiante.
     *
     * @param studentId El identificador único del estudiante a eliminar.
     * @return Un [ModelResult] que contiene un mensaje de confirmación en caso de éxito,
     * o un estado de error específico en caso de fallo.
     *
     * Posibles errores:
     * - [ModelError] de red si hay problemas de conexión
     * - [ModelError] de validación si el estudiante no existe o no se puede eliminar
     *
     * @example
     * ```
     * val result = deleteStudentUseCase(123)
     * when (result) {
     *     is SuccessResult -> println("Estudiante eliminado: ${result.data}")
     *     is ErrorResult -> println("Error: ${result.error}")
     * }
     * ```
     */
    suspend operator fun invoke (studentId: Int): ModelResult<String, ModelError> {
        return deleteStudentRepository.delete(studentId)
    }
}