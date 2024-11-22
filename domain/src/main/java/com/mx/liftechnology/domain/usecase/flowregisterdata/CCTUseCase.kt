package com.mx.liftechnology.domain.usecase.flowregisterdata

import androidx.lifecycle.LiveData
import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.ModelApi.GenericResponse
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.data.repository.mainFlow.CCTRepository

class CCTUseCase(
    private val cctRepository : CCTRepository
) {
    suspend fun getCCT(): ModelState<GenericResponse<List<DataCCT?>?>> {
        return runCatching { cctRepository.executeCCT() }
            .fold(
                onSuccess = { data ->
                    if(data!=null) SuccessState (data)
                    else ErrorState(1)
                },
                onFailure = { exception ->
                    ErrorState( 1)
                }
            )
    }

    /** Validate CCT
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateCCT(
        cct: String?,
        responseCCT: LiveData<ModelState<List<DataCCT?>?>>
    ): ModelState<Int> {
        return when {
            cct.isNullOrEmpty() -> {
                ErrorState(ModelCodeError.ET_EMPTY)
            }

            !validCCT(cct, responseCCT) -> {
                ErrorState(ModelCodeError.ET_NOT_FOUND)
            }

            else -> {
                SuccessState(ModelCodeSuccess.ET_FORMAT)
            }
        }
    }

    private fun validCCT(cct: String, responseCCT: LiveData<ModelState<List<DataCCT?>?>>): Boolean {
        val currentState = responseCCT.value
        return when (currentState) {
            is SuccessState -> {
                currentState.result?.any { cct == it?.cct } ?: false
            }

            else -> false
        }
    }
}