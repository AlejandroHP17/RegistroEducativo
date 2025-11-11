/**
 * @file Define el caso de uso para obtener la lista de estudiantes para una asignación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.student.GetStudentRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.student.toModelStudentList

/**
 * Caso de uso para obtener la lista de estudiantes para el registro de una nueva asignación.
 * Encapsula la lógica de negocio para solicitar la lista de estudiantes, procesarla y manejar los errores.
 *
 * @property getStudentRepository El repositorio para obtener la lista de estudiantes.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListStudentAssignmentUseCase (
    private val getStudentRepository: GetStudentRepository,
    private val preference: PreferenceUseCase
){
    /**
     * Ejecuta el proceso para obtener la lista de estudiantes de una asignación.
     *
     * @return Un [ResultModel] que contiene la lista de estudiantes formateada para el registro de asignación,
     * o un estado de error si la operación falla.
     */
    suspend operator fun invoke(): ModelResult<List<ModelStudentDomain>?, Error> {
        val cycleSchoolId =
            preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if(cycleSchoolId == null) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        return runCatching { getStudentRepository.executeGetListStudent(cycleSchoolId) }.fold(
            onSuccess = { result ->
                when(result){
                    is SuccessResult -> {
                        if (result.data.isNullOrEmpty()) ErrorResult(LocalError.EMPTY)
                        else SuccessResult(result.data.toModelStudentList())
                    }
                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkError.UNKNOWN)}
        )
    }
}