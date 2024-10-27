package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.ModelApi.DataCCT
import com.mx.liftechnology.core.util.ErrorState
import com.mx.liftechnology.core.util.GenericResponse
import com.mx.liftechnology.core.util.ModelState
import com.mx.liftechnology.core.util.SuccessState
import com.mx.liftechnology.data.repository.flowLogin.RegisterRepository

class RegisterUseCase(
    private val registerRepository : RegisterRepository
) {
    suspend fun getCCT():ModelState<GenericResponse<DataCCT>?>{
        return runCatching { registerRepository.executeCCT() }
            .fold(
                onSuccess = { data ->
                    if(data!=null) SuccessState (data)
                    else ErrorState(1)},
                onFailure = { exception ->
                    ErrorState( 1)
                }
            )
    }
}