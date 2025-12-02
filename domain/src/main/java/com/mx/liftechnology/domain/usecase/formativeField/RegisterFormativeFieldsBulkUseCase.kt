package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.network.api.RequestEvaluations
import com.mx.liftechnology.core.network.api.RequestWorkType
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.toFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.ModelSpinnersWorkMethods
import com.mx.liftechnology.domain.repository.formativeFields.RegisterFormativeFieldsBulkRepository

/**
 * @file Define el caso de uso para registrar una nueva materia.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Caso de uso para registrar una única materia.
 * Encapsula la lógica de negocio para construir la petición de registro y manejar la respuesta del repositorio.
 *
 * @property registerFormativeFieldsBulkRepository El repositorio para las operaciones de registro de materias.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterFormativeFieldsBulkUseCase(
    private val registerFormativeFieldsBulkRepository: RegisterFormativeFieldsBulkRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Ejecuta el proceso de registro de una materia con sus tipos de trabajo y evaluaciones asociadas.
     * Valida que existan los datos necesarios (ciclo escolar, parcial y lista de métodos de trabajo)
     * y registra la materia junto con sus configuraciones de evaluación.
     *
     * @param updatedList La lista de métodos de trabajo y sus porcentajes de evaluación. No puede ser nula o vacía.
     * @param name El nombre de la materia a registrar.
     * @return Un [ModelResult] que contiene los datos del campo formativo registrado ([FormativeFieldDomain])
     * en caso de éxito, o un estado de error específico en caso de fallo.
     *
     * Posibles errores:
     * - [LocalModelError.USER_INCOMPLETE_DATA] si faltan datos necesarios (cycleSchoolId, partialId) o si updatedList es nula o vacía
     * - [LocalModelError.EMPTY] si los datos transformados resultan en un campo formativo nulo
     * - [ModelError] de red si hay problemas de conexión
     * - [ModelError] de validación si los datos proporcionados no son válidos
     *
     * @example
     * ```
     * val workMethods = mutableListOf(
     *     ModelSpinnersWorkMethods(
     *         workTypeId = 1,
     *         name = ModelStateOutFieldText("Examen", false, ""),
     *         percent = ModelStateOutFieldText("40", false, "")
     *     ),
     *     ModelSpinnersWorkMethods(
     *         workTypeId = 2,
     *         name = ModelStateOutFieldText("Tarea", false, ""),
     *         percent = ModelStateOutFieldText("60", false, "")
     *     )
     * )
     * val result = registerFormativeFieldsBulkUseCase(
     *     updatedList = workMethods,
     *     name = "Matemáticas"
     * )
     * when (result) {
     *     is SuccessResult -> println("Materia registrada: ${result.data.name}")
     *     is ErrorResult -> println("Error: ${result.error}")
     * }
     * ```
     */
    suspend operator fun invoke(
        updatedList: MutableList<ModelSpinnersWorkMethods>?,
        name: String
    ): ModelResult<FormativeFieldDomain, ModelError> {
        val partialId = preference.getIdPartial()
        val cycleSchoolId = preference.getIdCycleSchool()

        if(cycleSchoolId == null || partialId == null || updatedList.isNullOrEmpty()) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        val workTypes : MutableList<RequestWorkType> = mutableListOf()
        val evaluations : MutableList<RequestEvaluations> = mutableListOf()

        updatedList.forEach { data ->
            workTypes.add(
                RequestWorkType(
                    workTypeId = data.workTypeId?.let { if(it > 1)data.workTypeId else null },
                    workTypeName = data.name.valueText.trim()
                )
            )

            evaluations.add(
                RequestEvaluations(
                    partialId = partialId,
                    workTypeId = data.workTypeId?.let { if(it > 0)data.workTypeId else null },
                    workTypeName = data.name.valueText.trim(),
                    evaluationWeight = data.percent.valueText.toInt()
                )
            )
        }


        val result = registerFormativeFieldsBulkRepository.registerBulk(
            cycleSchoolId = cycleSchoolId,
            formativeFieldName = name.trim(),
            code = name.trim(),
            workTypes = workTypes,
            evaluations = evaluations,
        )
        return when (result) {
            is SuccessResult -> {
                val domainData = result.data.toFormativeFieldDomain()
                if (domainData != null) {
                    SuccessResult(domainData)
                } else {
                    ErrorResult(LocalModelError.EMPTY)
                }
            }
            is ErrorResult -> result
        }
    }
}