package com.mx.liftechnology.domain.usecase.flowregisterdata

import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.modelBase.EmptyState
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.data.repository.mainFlow.CCTRepository

interface CCTUseCase {
    suspend fun getCCT(): ModelState<List<DataCCT?>?, String>?
    suspend fun validateCCT(cct: String?,
                            responseCCT: ModelState<List<DataCCT?>?, String>?): ModelState<Int, Int>?
}

class CCTUseCaseImp(
    private val cctRepository : CCTRepository
) : CCTUseCase {

    override suspend fun getCCT(
    ): ModelState<List<DataCCT?>?, String> {
        return when (val result = cctRepository.executeCCT()) {
            is SuccessState -> {
                if (result.result.isNullOrEmpty()) {
                    EmptyState(ModelCodeError.ERROR_EMPTY)
                } else {
                    SuccessState(result.result)
                }
            }
            is ErrorState -> ErrorState(result.result)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /** Validate CCT
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun validateCCT(
        cct: String?,
        responseCCT: ModelState<List<DataCCT?>?, String>?
    ): ModelState<Int, Int> {
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


    private fun validCCT(cct: String, responseCCT: ModelState<List<DataCCT?>?, String>?): Boolean {
        return when (responseCCT) {
            is SuccessState -> {
                responseCCT.result?.any { cct == it?.cct } ?: false
            }
            else -> false
        }
    }
}