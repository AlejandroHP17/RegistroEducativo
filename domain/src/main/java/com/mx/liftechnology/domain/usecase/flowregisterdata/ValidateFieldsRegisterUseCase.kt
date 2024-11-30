package com.mx.liftechnology.domain.usecase.flowregisterdata

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelRegex
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState

interface ValidateFieldsRegisterUseCase {
    fun validateGrade(email: String?): ModelState<Int, Int>
    fun validateGroup(pass: String?): ModelState<Int, Int>
    fun validatePeriod(pass: String?): ModelState<Int, Int>
}

class ValidateFieldsRegisterUseCaseImp : ValidateFieldsRegisterUseCase {
    /** validateEmail
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateGrade(email: String?): ModelState<Int, Int> {
        val patEmail = ModelRegex.EMAIL
        return when {
            email.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            !patEmail.matches(email) -> ErrorState(ModelCodeError.ET_FORMAT)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }


    /** validatePass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateGroup(pass: String?): ModelState<Int, Int> {
        return when {
            pass.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    /** Validate Pass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePeriod(pass: String?): ModelState<Int, Int> {
        return when {
            pass.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }
}

