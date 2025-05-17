package com.mx.liftechnology.domain.usecase.loginflowdomain

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

interface ValidateFieldsLoginUseCase {
    fun validateEmailCompose(email: String?): ModelStateOutFieldText
    fun validatePassCompose(pass: String?): ModelStateOutFieldText
    fun validatePassRegisterCompose(pass: String?): ModelStateOutFieldText
    fun validateRepeatPassCompose(pass: String?, repeatPass: String?): ModelStateOutFieldText
    fun validateCodeCompose(code: String?): ModelStateOutFieldText
}

class ValidateFieldsLoginUseCaseImp : ValidateFieldsLoginUseCase {
    /** validateEmail
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateEmailCompose(email: String?): ModelStateOutFieldText {
        val emailPattern = ModelRegex.EMAIL

        return when {
            email.isNullOrEmpty() -> email.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            !emailPattern.matches(email) -> email.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_USER_FORMAT_MISTAKE
            )

            else -> email.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validatePassLogin
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePassCompose(pass: String?): ModelStateOutFieldText {
        return when {
            pass.isNullOrEmpty() -> pass.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> pass.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** Validate PassRegister
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePassRegisterCompose(pass: String?): ModelStateOutFieldText {
        return when {
            pass.isNullOrEmpty() -> pass.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            !validPass(pass) -> pass.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_PASS_FORMAT_MISTAKE
            )

            else -> pass.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
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
    override fun validateRepeatPassCompose(
        pass: String?,
        repeatPass: String?,
    ): ModelStateOutFieldText {
        return when {
            repeatPass.isNullOrEmpty() -> pass.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            repeatPass != pass -> pass.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_PASS_DIFFERENT_MISTAKE
            )

            else -> pass.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** Validate Code
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCodeCompose(code: String?): ModelStateOutFieldText {
        return when {
            code.isNullOrEmpty() -> code.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> code.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}