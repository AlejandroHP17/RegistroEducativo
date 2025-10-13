package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool
import com.mx.liftechnology.data.repository.flowMain.school.GetCctRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.registerschool.ModelResultSchoolDomain
import com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain

/**
 * Use case for validating a CCT (Clave de Centro de Trabajo) and retrieving school information.
 *
 * @property getCctRepository The repository for CCT validation.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetCctUseCase(
    private val getCctRepository: GetCctRepository,
) {

    /**
     * Executes the CCT validation process.
     *
     * @param cct The CCT string to validate.
     * @return A [ModelState] containing the school information or an error.
     */
    suspend operator fun invoke(cct: String): ModelState<ModelResultSchoolDomain?, String> {
        return runCatching { getCctRepository.executeGetCct(cct) }.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        val response = ModelResultSchoolDomain(
                            buildLogicSpinner(result.data),
                            result.data
                        )

                        SuccessState(response)
                    }

                    is ResultError -> {
                        handleResponseCompose(result.error)
                    }
                }
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_UNKNOWN) }
        )
    }

    /**
     * Handles error responses from the CCT repository.
     *
     * @param error The [FailureService] object representing the error.
     * @return A [ModelState] representing the specific error.
     */
    private fun handleResponseCompose(error: FailureService): ModelState<ModelResultSchoolDomain?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /**
     * Builds the data models for spinners based on the school information.
     * It generates lists for school cycle, grade, and group based on the school type.
     *
     * @param data The [ResponseCctSchool] data received from the repository.
     * @return A [ModelSpinnerSchoolDomain] containing lists of strings for the spinners.
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