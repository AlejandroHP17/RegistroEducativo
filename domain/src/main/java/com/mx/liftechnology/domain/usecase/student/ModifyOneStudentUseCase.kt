/**
 * @file Define el caso de uso para modificar la información de un estudiante.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.student.RegisterStudentRepository
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult

/**
 * Interfaz para el caso de uso que modifica la información de un solo estudiante.
 * Define el contrato para la lógica de negocio de la modificación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface ModifyOneStudentUseCase{
    /**
     * Modifica la información de un estudiante existente.
     *
     * @param name El nuevo nombre del estudiante.
     * @param lastName El nuevo apellido paterno del estudiante.
     * @param secondLastName El nuevo apellido materno del estudiante.
     * @param curp La nueva CURP del estudiante.
     * @param birthday La nueva fecha de nacimiento del estudiante.
     * @param phoneNumber El nuevo número de teléfono del estudiante.
     * @return Un [ModelResult] que indica el resultado de la operación de modificación.
     */
    suspend fun modifyOneStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String
    ): ModelResult<List<String?>?, ModelError>?
}

/**
 * Implementación de [ModifyOneStudentUseCase].
 *
 * @property crudStudentRepository El repositorio para las operaciones CRUD de estudiantes.
 * @property preference El caso de uso para la gestión de preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ModifyOneStudentUseCaseImp (
    private val crudStudentRepository: RegisterStudentRepository,
    private val preference: PreferenceUseCase
):ModifyOneStudentUseCase {
    /**
     * {@inheritDoc}
     */
    override suspend fun modifyOneStudent(
        name: String,
        lastName: String,
        secondLastName: String,
        curp: String,
        birthday: String,
        phoneNumber: String,
    ): ModelResult<List<String?>?, ModelError>? {
        TODO("Aún no implementado")
    }

}
