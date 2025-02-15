package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelRegex
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState

interface ValidateFieldsLoginUseCase {
    fun validateEmail(email: String?): ModelState<Int, Int>
    fun validatePass(pass: String?): ModelState<Int, Int>
    fun validatePassRegister(pass: String?): ModelState<Int, Int>
    fun validateRepeatPass(pass: String?, repeatPass: String?): ModelState<Int, Int>
    fun validateCode(code: String?): ModelState<Int, Int>
}

class ValidateFieldsLoginUseCaseImp : ValidateFieldsLoginUseCase {
    /** validateEmail
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateEmail(email: String?): ModelState<Int, Int> {
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
    override fun validatePass(pass: String?): ModelState<Int, Int> {
        return when {
            pass.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    /** Validate Pass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePassRegister(pass: String?): ModelState<Int, Int> {
        return when {
            pass.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            !validPass(pass) -> ErrorState(ModelCodeError.ET_MISTAKE_PASS_RULES)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    private fun validPass(pass: String): Boolean {
        val regex = ModelRegex.PASS
        return regex.matches(pass)
    }

    /** Validate RepeatPass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateRepeatPass(pass: String?, repeatPass: String?):  ModelState<Int,Int> {
        return when {
            repeatPass.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            repeatPass != pass -> ErrorState(ModelCodeError.ET_DIFFERENT)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    /** Validate Code
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCode(code: String?):  ModelState<Int,Int> {
        return when {
            code.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }
}

