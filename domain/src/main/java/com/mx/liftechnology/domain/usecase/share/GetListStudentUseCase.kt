package com.mx.liftechnology.domain.usecase.share

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.model.student.toStudentDomainList
import com.mx.liftechnology.domain.repository.student.GetStudentRepository

/**
 * Caso de uso para obtener la lista de estudiantes.
 * Encapsula la lógica de negocio para solicitar la lista de estudiantes, procesarla y manejar los errores.
 *
 * @property getStudentRepository El repositorio para obtener la lista de estudiantes.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListStudentUseCase(
    private val getStudentRepository: GetStudentRepository,
    private val preference: PreferenceUseCase
)  {
    /**
     * Ejecuta el proceso para obtener la lista de estudiantes.
     *
     * @return Un [com.mx.liftechnology.core.util.models.ModelResult] que contiene la lista de estudiantes o un estado de error.
     */
    suspend operator fun invoke(): ModelResult<List<StudentDomain>, ModelError> {
        val cycleSchoolId =
            preference.getIdCycleSchool() ?: return ErrorResult(
                LocalModelError.USER_INCOMPLETE_DATA
            )

        val result = getStudentRepository.getStudents(cycleSchoolId)
        return when (result) {
            is SuccessResult -> {
                if (result.data.isEmpty()) ErrorResult(LocalModelError.EMPTY)
                else SuccessResult(result.data.toStudentDomainList())
            }
            is ErrorResult -> result
        }
    }
}