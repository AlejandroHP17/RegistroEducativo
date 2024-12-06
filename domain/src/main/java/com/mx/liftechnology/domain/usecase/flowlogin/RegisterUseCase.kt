package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.data.repository.loginFlow.RegisterRepository

fun interface RegisterUseCase {
    suspend fun putRegister(email: String, pass: String, activatationCode: String): ModelState<String?, String>?
}

class RegisterUseCaseImp(
    private val registerRepository: RegisterRepository
) : RegisterUseCase {

    /** Request to Register
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun putRegister(
        email: String, pass: String, activatationCode: String
    ):  ModelState<String?, String> {
        return registerRepository.executeRegister(email.lowercase(), pass, activatationCode)
    }
}