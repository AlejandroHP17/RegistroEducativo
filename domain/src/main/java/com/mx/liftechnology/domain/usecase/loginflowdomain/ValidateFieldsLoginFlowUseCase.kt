package com.mx.liftechnology.domain.usecase.loginflowdomain

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Interface for validating fields in the login and registration flows.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsLoginFlowUseCase {
    /**
     * Validates an email address.
     * @param email The email to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateEmailCompose(email: String?): ModelStateOutFieldText

    /**
     * Validates a password for login.
     * @param pass The password to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validatePassCompose(pass: String?): ModelStateOutFieldText

    /**
     * Validates a password for registration.
     * @param pass The password to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validatePassRegisterCompose(pass: String?): ModelStateOutFieldText

    /**
     * Validates that two passwords match.
     * @param pass The original password.
     * @param repeatPass The password to compare against.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateRepeatPassCompose(pass: String?, repeatPass: String?): ModelStateOutFieldText

    /**
     * Validates an activation code.
     * @param code The code to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateCodeCompose(code: String?): ModelStateOutFieldText
}

/**
 * Implementation of [ValidateFieldsLoginFlowUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsLoginFlowUseCaseImp : ValidateFieldsLoginFlowUseCase {
    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    override fun validatePassCompose(pass: String?): ModelStateOutFieldText {
        return when {
            pass.isNullOrEmpty() -> pass.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> pass.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
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

    private fun validPass(pass: String): Boolean = ModelRegex.PASS.matches(pass)


    /**
     * {@inheritDoc}
     */
    override fun validateRepeatPassCompose(
        pass: String?,
        repeatPass: String?,
    ): ModelStateOutFieldText {
        return when {
            repeatPass.isNullOrEmpty() -> repeatPass.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            repeatPass != pass -> repeatPass.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_PASS_DIFFERENT_MISTAKE
            )

            else -> repeatPass.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
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