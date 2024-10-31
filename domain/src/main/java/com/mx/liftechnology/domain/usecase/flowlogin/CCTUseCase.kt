package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.ModelApi.GenericResponse
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.data.repository.flowLogin.RegisterRepository

class CCTUseCase(
    private val registerRepository : RegisterRepository
) {
    suspend fun getCCT(): ModelState<GenericResponse<List<DataCCT?>?>> {
        return runCatching { registerRepository.executeCCT() }
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
}