package com.mx.liftechnology.domain.usecase.flowlogin

import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

interface ValidateFieldsLoginUseCase {
    fun validateEmail(email: String?): ModelState<String, String>
    fun validatePass(pass: String?): ModelState<String, String>
    fun validatePassRegister(pass: String?): ModelState<String, String>
    fun validateRepeatPass(pass: String?, repeatPass: String?): ModelState<String, String>
    fun validateCode(code: String?): ModelState<String, String>
}

class ValidateFieldsLoginUseCaseImp : ValidateFieldsLoginUseCase {
    /** validateEmail
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateEmail(email: String?): ModelState<String, String> {
        val emailPattern = ModelRegex.EMAIL

        return when {
            email.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            !emailPattern.matches(email) -> ErrorUserState(ModelCodeInputs.ET_USER_FORMAT_MISTAKE)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validatePassLogin
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePass(pass: String?): ModelState<String, String> {
        return when {
            pass.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** Validate PassRegister
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePassRegister(pass: String?): ModelState<String, String> {
        return when {
            pass.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            !validPass(pass) -> ErrorUserState(ModelCodeInputs.ET_PASS_FORMAT_MISTAKE)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
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
    override fun validateRepeatPass(pass: String?, repeatPass: String?): ModelState<String, String> {
        return when {
            repeatPass.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            repeatPass != pass -> ErrorUserState(ModelCodeInputs.ET_PASS_DIFFERENT_MISTAKE)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** Validate Code
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCode(code: String?): ModelState<String, String> {
        return when {
            code.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}

