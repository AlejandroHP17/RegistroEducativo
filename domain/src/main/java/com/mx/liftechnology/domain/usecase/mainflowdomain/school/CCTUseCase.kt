package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.core.network.callapi.ResponseCctSchool
import com.mx.liftechnology.data.repository.mainflowdata.CCTRepository
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

interface CCTUseCase {
    suspend fun getSchoolCCT(cct: String): ModelState<ResponseCctSchool?, String>
    suspend fun getSchoolCCTCompose(cct: String): ModelState<ModelResultSchoolDomain?, String>
}

class CCTUseCaseImp(
    private val cctRepository : CCTRepository
) : CCTUseCase {

    /** Validate CCT
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun getSchoolCCTCompose(cct: String): ModelState<ModelResultSchoolDomain?, String> {
        return when (val result =  cctRepository.executeSchoolCCT(cct)) {
            is ResultSuccess -> {
                val response =  ModelResultSchoolDomain(
                    buildLogicSpinner(result.data),
                    result.data)

                SuccessState(response)
            }
            is ResultError -> {
                handleResponseCompose(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
    override suspend fun getSchoolCCT(cct: String): ModelState<ResponseCctSchool?, String> {
        return when (val result =  cctRepository.executeSchoolCCT(cct)) {
            is ResultSuccess -> {
                SuccessState(result.data)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /**
     * Maneja la respuesta del servidor y retorna el estado adecuado.
     */
    private fun handleResponseCompose(error: FailureService): ModelState<ModelResultSchoolDomain?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }    private fun handleResponse(error: FailureService): ModelState<ResponseCctSchool?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

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