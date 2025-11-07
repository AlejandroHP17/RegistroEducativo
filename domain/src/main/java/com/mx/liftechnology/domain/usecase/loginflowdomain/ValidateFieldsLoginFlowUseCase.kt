/**
 * @file Proporciona el caso de uso para la validación de campos en los flujos de login y registro.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.loginflowdomain

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText

/**
 * Interfaz para el caso de uso que valida los campos de los flujos de login y registro.
 * Define el contrato para las diferentes validaciones de email, contraseña y códigos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsLoginFlowUseCase {
    /**
     * Valida una dirección de correo electrónico.
     * @param email El email a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateEmailCompose(email: String?): ModelStateOutFieldText

    /**
     * Valida una contraseña para el inicio de sesión.
     * @param pass La contraseña a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validatePassCompose(pass: String?): ModelStateOutFieldText

    /**
     * Valida una contraseña para el registro de un nuevo usuario.
     * @param pass La contraseña a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validatePassRegisterCompose(pass: String?): ModelStateOutFieldText

    /**
     * Valida que dos contraseñas coincidan.
     * @param pass La contraseña original.
     * @param repeatPass La contraseña con la que se debe comparar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateRepeatPassCompose(pass: String?, repeatPass: String?): ModelStateOutFieldText

    /**
     * Valida un código de activación.
     * @param code El código a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateCodeCompose(code: String?): ModelStateOutFieldText
}

/**
 * Implementación de [ValidateFieldsLoginFlowUseCase].
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