package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.ModelApi.Data
import com.mx.liftechnology.core.util.ErrorState
import com.mx.liftechnology.core.util.GenericResponse
import com.mx.liftechnology.core.util.ModelState
import com.mx.liftechnology.core.util.SuccessState
import com.mx.liftechnology.data.repository.flowLogin.LoginRepository

class LoginUseCase(
    private val repositoryLogin : LoginRepository
) {


    suspend fun login(email: String?, pass: String?):ModelState<GenericResponse<Data>?>{
        return runCatching { repositoryLogin.execute(email, pass) }
            .fold(
                onSuccess = { data -> SuccessState (data) },
                onFailure = { exception ->
                    ErrorState( 1)
                }
            )
    }
}