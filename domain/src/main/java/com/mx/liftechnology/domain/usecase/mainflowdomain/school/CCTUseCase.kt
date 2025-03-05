package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.core.network.callapi.ResponseCctSchool
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.data.repository.mainflowdata.CCTRepository

fun interface CCTUseCase {
    suspend fun getSchoolCCT(cct: String): ModelState<ResponseCctSchool?, String>
}

class CCTUseCaseImp(
    private val cctRepository : CCTRepository
) : CCTUseCase {

    /** Validate CCT
     * @author pelkidev
     * @since 1.0.0
     * */
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
    private fun handleResponse(error: FailureService): ModelState<ResponseCctSchool?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

}