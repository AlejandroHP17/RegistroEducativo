/**
 * @file Define el caso de uso para obtener la lista de asignaciones (trabajos) por materia.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetPercentSubjectId
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPercentSubjectId
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.assignment.GetPercentSubjectRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.subject.ModelFormatAssignment

/**
 * Caso de uso para obtener la lista de asignaciones (trabajos) por materia.
 * Encapsula la lógica de negocio para solicitar la información desde el repositorio y transformarla para la UI.
 *
 * @property getPercentSubjectRepository El repositorio para obtener los datos de las asignaciones.
 * @property preference El caso de uso para acceder a las preferencias del usuario (IDs de sesión, etc.).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssignmentPerSubjectUseCase (
    private val getPercentSubjectRepository: GetPercentSubjectRepository,
    private val preference : PreferenceUseCase
) {
    /**
     * Ejecuta el proceso para obtener la lista de asignaciones por materia.
     * Construye la petición, la envía al repositorio, mapea la respuesta y maneja los errores.
     *
     * @return Un [ResultModel] que contiene la lista de [ModelFormatAssignment] en caso de éxito,
     * o un estado de error específico en caso de fallo.
     */
    suspend operator fun invoke(): ResultModel<List<ModelFormatAssignment>?, String?>{

        val teacherId = preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val teacherSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)
        val subjectSchoolCycleGroupId = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP)

        val request = RequestGetPercentSubjectId(
            teacherId = teacherId,
            userId = userId,
            teacherSchoolCycleGroupId = teacherSchoolCycleGroupId,
            subjectSchoolCycleGroupId = subjectSchoolCycleGroupId
        )

        return runCatching { getPercentSubjectRepository.executeGetPercentSubject(request) }.fold(
            onSuccess = { result->
                when(result) {
                    is ResultSuccess -> {
                        val data = result.data.toModelUseCase()
                        if(data.isNotEmpty()) SuccessResult(data)
                        else ErrorResult(ModelCodeError.ERROR_EMPTY) // Devuelve un error específico si la lista está vacía.
                    }
                    is ResultError -> handleResponse(result.error)
                }
            },
            onFailure = {ErrorResult(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    /**
     * Convierte una lista de [ResponseGetPercentSubjectId] a una lista de [ModelFormatAssignment].
     *
     * @receiver Lista de respuestas de la API.
     * @return Lista de modelos de dominio formateados.
     */
    private fun List<ResponseGetPercentSubjectId>?.toModelUseCase() : List<ModelFormatAssignment> {
        return this?.map {
            ModelFormatAssignment(
                id = it.id,
                percent= it.percent,
                subjectSchoolCycleGroupId = it.subjectSchoolCycleGroupId,
                description	= it.description.stringToModelStateOutFieldText(),
                teacherSchoolCycleGroupId= it.teacherSchoolCycleGroupId,
                assignmentId= it.assignmentId,
                assignmentName= it.assignmentName.stringToModelStateOutFieldText()
            )
        }?: emptyList()
    }

    /**
     * Maneja las respuestas de error del repositorio, convirtiendo un [FailureService] en un [ResultModel] específico.
     *
     * @param error El objeto [FailureService] que representa el error de la capa de datos.
     * @return Un [ResultModel] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: FailureService): ResultModel<List<ModelFormatAssignment>?, String?> {
        return when(error) {
            is FailureService.BadRequest -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.NotFound -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_INFO)
            is FailureService.Timeout -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}