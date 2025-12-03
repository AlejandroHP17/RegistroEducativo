package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.domain.model.generic.ModelValidationResult

/**
 * Caso de uso que combina la validación de campos de registro de usuario con la ejecución del registro.
 * Encapsula la lógica de negocio de validación + operación, retornando los estados de validación
 * para que el ViewModel pueda actualizar la UI.
 *
 * @property validateFieldsUseCase El caso de uso para validar los campos del formulario.
 * @property registerUserUseCase El caso de uso para ejecutar el registro de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserWithValidationUseCase(
    private val validateFieldsUseCase: ValidateAuthFieldsUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) {
    /**
     * Valida los campos del formulario de registro de usuario y, si son válidos, ejecuta el registro.
     *
     * @param email El correo electrónico del usuario.
     * @param pass La contraseña del usuario.
     * @param repeatPass La confirmación de la contraseña.
     * @param activationCode El código de activación.
     * @return Un [ModelValidationResult] que contiene:
     * - Los estados de validación de cada campo (para actualizar la UI)
     * - El resultado de la operación de registro (solo si todas las validaciones pasaron)
     * - Un flag que indica si todas las validaciones pasaron
     */
    suspend operator fun invoke(
        email: String?,
        pass: String?,
        repeatPass: String?,
        activationCode: String?
    ): ModelValidationResult<Boolean> {
        // 1. Validar todos los campos
        val emailState = validateFieldsUseCase.validateEmailCompose(email)
        val passState = validateFieldsUseCase.validatePassRegisterCompose(pass)
        val repeatPassState = validateFieldsUseCase.validateRepeatPassCompose(pass, repeatPass)
        val codeState = validateFieldsUseCase.validateCodeCompose(activationCode)

        val validationStates = mapOf(
            "email" to emailState,
            "pass" to passState,
            "repeatPass" to repeatPassState,
            "code" to codeState
        )

        // 2. Verificar si hay errores de validación
        val hasErrors = emailState.isError || passState.isError || repeatPassState.isError || codeState.isError

        // 3. Si hay errores, retornar resultado de validación fallida
        if (hasErrors) {
            return ModelValidationResult.invalid(validationStates)
        }

        // 4. Si todas las validaciones pasaron, ejecutar la operación
        val operationResult = registerUserUseCase.invoke(email, pass, activationCode)

        // 5. Retornar resultado con validación exitosa y resultado de la operación
        return ModelValidationResult.valid(validationStates, operationResult)
    }
}

