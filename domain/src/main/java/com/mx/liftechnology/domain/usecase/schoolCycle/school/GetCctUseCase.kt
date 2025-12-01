package com.mx.liftechnology.domain.usecase.schoolCycle.school

import com.mx.liftechnology.core.util.extension.logInfo
import com.mx.liftechnology.data.model.schoolCycle.ModelCCTData
import com.mx.liftechnology.data.repository.schoolCycle.school.GetCctRepository
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.registerschool.ModelResultSchoolDomain
import com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain

/**
 * @file Define el caso de uso para validar una CCT (Clave de Centro de Trabajo) y obtener la información de la escuela.
 * @author Pelkidev
 * @version 1.0.0
 */
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
     * @return Un [com.mx.liftechnology.data.util.ModelResult] que contiene la información de la escuela o un estado de error.
     */
    suspend operator fun invoke(cct: String): ModelResult<ModelResultSchoolDomain, ModelError> {
        return runCatching { getCctRepository.executeGetCct(cct) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        val response = ModelResultSchoolDomain(
                            buildLogicSpinner(result.data),
                            result.data
                        )
                        SuccessResult(response)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }


    /**
     * Construye los modelos de datos para los spinners basándose en la información de la escuela.
     * Genera listas para el ciclo escolar, el grado y el grupo según el tipo de escuela.
     *
     * @param data Los datos de [com.mx.liftechnology.core.network.api.SchoolCycleApi.ResponseCctSchool] recibidos del repositorio.
     * @return Un [com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain] que contiene las listas de strings para los spinners.
     */
    private fun buildLogicSpinner(data: ModelCCTData): ModelSpinnerSchoolDomain {

        // --- TYPE (Anual, Semestral, etc) ---
        val types = data.periodCatalog
            .map { it.typeName }
            .distinct()
            .mapIndexed { index, type ->
                ModelCustomSpinner(value = type, id = index + 1)
            }

        logInfo(types.toString())

        // --- CYCLE (número de periodos del año: 1, 2, 3...) ---
        val cycles = data.periodCatalog
            .map { it.periodNumber }
            .distinct()
            .map { number ->
                ModelCustomSpinner(value = number.toString(), id = number)
            }

        // --- GRADE (según tipo de escuela) ---
        val gradeCount = when (data.schoolTypeId) {
            1, 2 -> 6
            3 -> 3
            4 -> 6
            5 -> 12
            else -> 0
        }

        val grades = if (gradeCount > 0) {
            (1..gradeCount).map {
                ModelCustomSpinner(value = it.toString(), id = it)
            }
        } else emptyList()

        // --- GROUP (A, B, C, etc) ---
        val groupCount = when (data.schoolTypeId) {
            1, 2 -> 4
            3 -> 12
            4, 5 -> 10
            else -> 0
        }

        val groups = if (groupCount > 0) {
            ('A'..'Z').take(groupCount).mapIndexed { index, letter ->
                ModelCustomSpinner(value = letter.toString(), id = index + 1)
            }
        } else emptyList()

        return ModelSpinnerSchoolDomain(
            type = types,
            cycle = cycles,
            grade = grades,
            group = groups
        )
    }
}