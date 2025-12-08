package com.mx.liftechnology.domain.usecase.auth

import com.mx.liftechnology.domain.model.auth.UserDomain
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.usecase.share.ValidateAuthFieldsUseCase

/**
 * Caso de uso que combina la validación de campos de login con la ejecución del login.
 * Encapsula la lógica de negocio de validación + operación, retornando los estados de validación
 * para que el ViewModel pueda actualizar la UI.
 *
 * @property validateFieldsUseCase El caso de uso para validar los campos del formulario.
 * @property loginUseCase El caso de uso para ejecutar el login.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginWithValidationUseCase(
    private val validateFieldsUseCase: ValidateAuthFieldsUseCase,
    private val loginUseCase: LoginUseCase
) {
    /**
     * Valida los campos del formulario de login y, si son válidos, ejecuta el login.
     *
     * @param email El correo electrónico del usuario.
     * @param pass La contraseña del usuario.
     * @param remember Indica si la sesión del usuario debe ser recordada.
     * @return Un [ModelValidationResult] que contiene:
     * - Los estados de validación de cada campo (para actualizar la UI)
     * - El resultado de la operación de login (solo si todas las validaciones pasaron)
     * - Un flag que indica si todas las validaciones pasaron
     */
    suspend operator fun invoke(
        email: String?,
        pass: String?,
        remember: Boolean = false
    ): ModelValidationResult<UserDomain> {
        // 1. Validar todos los campos
        val emailState = validateFieldsUseCase.validateEmailCompose(email)
        val passState = validateFieldsUseCase.validatePassCompose(pass)

        val validationStates = mapOf(
            "email" to emailState,
            "pass" to passState
        )

        // 2. Verificar si hay errores de validación
        val hasErrors = emailState.isError || passState.isError

        // 3. Si hay errores, retornar resultado de validación fallida
        if (hasErrors) {
            return ModelValidationResult.invalid(validationStates)
        }

        // 4. Si todas las validaciones pasaron, ejecutar la operación
        val operationResult = loginUseCase.invoke(email, pass, remember)

        // 5. Retornar resultado con validación exitosa y resultado de la operación
        return ModelValidationResult.valid(validationStates, operationResult)
    }
}

