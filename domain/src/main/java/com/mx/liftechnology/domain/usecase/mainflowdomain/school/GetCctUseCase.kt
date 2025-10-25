/**
 * @file Define el caso de uso para validar una CCT (Clave de Centro de Trabajo) y obtener la información de la escuela.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool
import com.mx.liftechnology.data.repository.flowMain.school.GetCctRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.registerschool.ModelResultSchoolDomain
import com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain

/**
 * Caso de uso para validar una CCT y obtener la información de una escuela.
 * Encapsula la lógica de negocio para interactuar con el repositorio y procesar la respuesta,
 * incluyendo la construcción de los datos para los spinners de la UI.
 *
 * @property getCctRepository El repositorio para la validación de la CCT.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetCctUseCase(
    private val getCctRepository: GetCctRepository,
) {

    /**
     * Ejecuta el proceso de validación de la CCT.
     *
     * @param cct La Clave de Centro de Trabajo a validar.
     * @return Un [ResultModel] que contiene la información de la escuela o un estado de error.
     */
    suspend operator fun invoke(cct: String): ResultModel<ModelResultSchoolDomain?, String> {
        return runCatching { getCctRepository.executeGetCct(cct) }.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        val response = ModelResultSchoolDomain(
                            buildLogicSpinner(result.data),
                            result.data
                        )

                        SuccessResult(response)
                    }

                    is ResultError -> {
                        handleResponseCompose(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(ModelCodeError.ERROR_UNKNOWN) }
        )
    }

    /**
     * Maneja las respuestas de error del repositorio de CCT.
     *
     * @param error El objeto [FailureService] que representa el error.
     * @return Un [ResultModel] que representa el error específico.
     */
    private fun handleResponseCompose(error: FailureService): ResultModel<ModelResultSchoolDomain?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedResult(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /**
     * Construye los modelos de datos para los spinners basándose en la información de la escuela.
     * Genera listas para el ciclo escolar, el grado y el grupo según el tipo de escuela.
     *
     * @param data Los datos de [ResponseCctSchool] recibidos del repositorio.
     * @return Un [ModelSpinnerSchoolDomain] que contiene las listas de strings para los spinners.
     */
    private fun buildLogicSpinner(data: ResponseCctSchool?): ModelSpinnerSchoolDomain {
        val cycle = data?.schoolCycleType.let { cycle ->
            val cycleMapping = mapOf(
                "Anual" to 1,
                "Bimestral" to 2,
                "Trimestral" to 3,
                "Cuatrimestral" to 4
            )
            val cycleCount = cycleMapping[cycle]
            cycleCount?.let { it -> (1..it).map { it.toString() } }
        }
        val grade = data?.schoolType.let { grade ->
            val gradeMapping = mapOf(
                "Primaria" to 6,
                "Secundaria" to 3,
                "Bachillerato" to 6,
                "Universidad" to 12
            )
            val gradeCount = gradeMapping[grade]
            gradeCount?.let { it -> (1..it).map { it.toString() } }
        }
        val group = data?.schoolType.let { group ->
            val groupMapping = mapOf(
                "Primaria" to 4,
                "Secundaria" to 12,
                "Bachillerato" to 10,
                "Universidad" to 10
            )
            val groupCount = groupMapping[group]
            groupCount?.let { it -> ('A'..'Z').take(groupCount).map { it.toString() } }
        }

        return ModelSpinnerSchoolDomain(cycle, grade, group)
    }

}