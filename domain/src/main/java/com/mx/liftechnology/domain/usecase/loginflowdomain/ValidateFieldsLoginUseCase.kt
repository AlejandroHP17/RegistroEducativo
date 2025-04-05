package com.mx.liftechnology.domain.usecase.loginflowdomain

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
            email.isNullOrEmpty() -> ModelStateOutFieldText(
                valueText = email ?: "",
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            !emailPattern.matches(email) -> ModelStateOutFieldText(
                valueText = email,
                isError = true,
                errorMessage = ModelCodeInputs.ET_USER_FORMAT_MISTAKE
            )

            else -> ModelStateOutFieldText(
                valueText = email,
                isError = false,
                errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
            )
        }
    }

    /** validatePassLogin
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePassCompose(pass: String?): ModelStateOutFieldText {
        return when {
            pass.isNullOrEmpty() -> ModelStateOutFieldText(
                valueText = pass ?: "",
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> ModelStateOutFieldText(
                valueText = pass,
                isError = false,
                errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
            )
        }
    }

    /** Validate PassRegister
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePassRegisterCompose(pass: String?): ModelStateOutFieldText {
        return when {
            pass.isNullOrEmpty() -> ModelStateOutFieldText(
                valueText = pass ?: "",
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            !validPass(pass) -> ModelStateOutFieldText(
                valueText = pass,
                isError = true,
                errorMessage = ModelCodeInputs.ET_PASS_FORMAT_MISTAKE
            )

            else -> ModelStateOutFieldText(
                valueText = pass,
                isError = false,
                errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
            )
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
            repeatPass.isNullOrEmpty() -> ModelStateOutFieldText(
                valueText = pass ?: "",
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            repeatPass != pass -> ModelStateOutFieldText(
                valueText = pass ?: "",
                isError = true,
                errorMessage = ModelCodeInputs.ET_PASS_DIFFERENT_MISTAKE
            )

            else -> ModelStateOutFieldText(
                valueText = pass,
                isError = false,
                errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
            )
        }
    }

    /** Validate Code
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCodeCompose(code: String?): ModelStateOutFieldText {
        return when {
            code.isNullOrEmpty() -> ModelStateOutFieldText(
                valueText = code ?: "",
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> ModelStateOutFieldText(
                valueText = code,
                isError = false,
                errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
            )
        }
    }
}

