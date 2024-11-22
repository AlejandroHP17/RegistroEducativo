package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelRegex
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState

class ForgetPasswordUseCase{

    /** Validate Email
     * @author pelkidev
     * @since 1.0.0
     * */
    fun validateEmail(email: String?): ModelState<Int> {
        val patEmail = ModelRegex.EMAIL
        return when {
            email.isNullOrEmpty() -> {
                ErrorState(ModelCodeError.ET_EMPTY)
            }

            !patEmail.matches(email) -> {
                ErrorState(ModelCodeError.ET_FORMAT)
            }

            else -> {
                SuccessState(ModelCodeSuccess.ET_FORMAT)
            }
        }
    }



}