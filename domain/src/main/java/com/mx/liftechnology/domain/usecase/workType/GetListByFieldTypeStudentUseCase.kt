package com.mx.liftechnology.domain.usecase.workType

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.ByFieldTypeStudentDomain
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository

/**
 * Caso de uso para obtener la lista de estudiantes filtrados por tipo de campo formativo.
 * Encapsula la lógica de negocio para recuperar estudiantes asociados a un campo formativo específico,
 * permitiendo filtrar por tipo de trabajo, nombre de trabajo y fecha.
 *
 * @property evaluationRepository El repositorio para operaciones relacionadas con evaluaciones.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListByFieldTypeStudentUseCase(
    private val evaluationRepository: EvaluationRepository,
    private val preference : PreferenceUseCase
) {

    /**
     * Ejecuta el proceso de obtención de la lista de estudiantes por tipo de campo formativo.
     * Obtiene el ID del campo formativo desde las preferencias y recupera los estudiantes
     * con los filtros proporcionados.
     *
     * @param workTypeId El identificador del tipo de trabajo. Requerido.
     * @param workName El nombre del trabajo para filtrar. Opcional.
     * @param workDate La fecha del trabajo para filtrar (formato de fecha). Opcional.
     * @return Un [ModelResult] que contiene los datos de estudiantes filtrados
     * ([ByFieldTypeStudentDomain]) en caso de éxito, o un estado de error específico en caso de fallo.
     *
     * Posibles errores:
     * - [LocalModelError.USER_INCOMPLETE_DATA] si no hay un campo formativo seleccionado en las preferencias o si workTypeId es null
     * - [NetworkModelError.UNKNOWN] si ocurre una excepción inesperada
     * - [ModelError] de red si hay problemas de conexión
     * - [ModelError] si no se encuentran estudiantes con los criterios proporcionados
     *
     * @example
     * ```
     * // Obtener estudiantes por tipo de trabajo
     * val result = getListByFieldTypeStudentUseCase(
     *     workTypeId = 1,
     *     workName = null,
     *     workDate = null
     * )
     *
     * // Obtener estudiantes con filtros específicos
     * val resultFiltered = getListByFieldTypeStudentUseCase(
     *     workTypeId = 1,
     *     workName = "Tarea 1",
     *     workDate = "2024-01-15"
     * )
     *
     * when (result) {
     *     is SuccessResult -> println("Estudiantes encontrados: ${result.data}")
     *     is ErrorResult -> println("Error: ${result.error}")
     * }
     * ```
     */
    suspend operator fun invoke(workTypeId: Int?, workName: String?, workDate: String?): ModelResult<ByFieldTypeStudentDomain, ModelError> {
        val formativeFieldId = preference.getIdFormativeField() ?: return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        if(workTypeId == null)return ErrorResult(LocalModelError.USER_INCOMPLETE_DATA)

        return runCatching { evaluationRepository.getByFieldType(
            formativeFieldId = formativeFieldId,
            workTypeId = workTypeId,
            workName = workName,
            workDate = workDate
            ) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        SuccessResult(result.data)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }
}