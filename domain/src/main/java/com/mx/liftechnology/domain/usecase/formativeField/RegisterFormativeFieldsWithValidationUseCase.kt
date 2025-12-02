package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.model.formativeFields.ModelSpinnersWorkMethods

/**
 * Caso de uso que combina la validación de campos de registro de materia formativa con la ejecución del registro.
 * Encapsula la lógica de negocio de validación + operación, retornando los estados de validación
 * para que el ViewModel pueda actualizar la UI.
 *
 * @property validateFieldsUseCase El caso de uso para validar los campos del formulario.
 * @property registerFormativeFieldsBulkUseCase El caso de uso para ejecutar el registro de materia formativa.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterFormativeFieldsWithValidationUseCase(
    private val validateFieldsUseCase: ValidateFieldsFormativeFieldsUseCase,
    private val registerFormativeFieldsBulkUseCase: RegisterFormativeFieldsBulkUseCase
) {
    data class ValidationResult(
        val validationResult: ModelValidationResult<FormativeFieldDomain>,
        val updatedListAdapter: MutableList<ModelSpinnersWorkMethods>?
    )

    /**
     * Valida los campos del formulario de registro de materia formativa y, si son válidos, ejecuta el registro.
     *
     * @param subject El nombre de la materia.
     * @param options El número de opciones (métodos de trabajo).
     * @param listAdapter La lista de métodos de trabajo con sus porcentajes.
     * @return Un [ValidationResult] que contiene:
     * - El resultado de validación con estados de validación y resultado de operación
     * - La lista de adaptadores actualizada con los estados de validación
     */
    suspend operator fun invoke(
        subject: String?,
        options: String?,
        listAdapter: MutableList<ModelSpinnersWorkMethods>?
    ): ValidationResult {
        // 1. Validar todos los campos
        val nameState = validateFieldsUseCase.validateNameCompose(subject)
        val optionState = validateFieldsUseCase.validateOptionCompose(options)
        val updatedListState = validateFieldsUseCase.validateListJobsCompose(listAdapter)

        val validationStates = mutableMapOf<String, ModelStateOutFieldText>(
            "subject" to nameState,
            "options" to optionState
        )

        // 2. Verificar si hay errores de validación básicos
        var hasErrors = nameState.isError || optionState.isError

        // 3. Si no hay errores básicos, validar porcentajes
        if (!hasErrors) {
            val percentState = validateFieldsUseCase.validPercentCompose(updatedListState)
            validationStates["options"] = percentState
            hasErrors = percentState.isError
        }

        // 4. Si hay errores, retornar resultado de validación fallida
        if (hasErrors) {
            return ValidationResult(
                validationResult = ModelValidationResult.invalid(validationStates),
                updatedListAdapter = updatedListState
            )
        }

        // 5. Si todas las validaciones pasaron, ejecutar la operación
        val operationResult = registerFormativeFieldsBulkUseCase.invoke(
            updatedList = updatedListState,
            name = subject ?: ""
        )

        // 6. Retornar resultado con validación exitosa y resultado de la operación
        return ValidationResult(
            validationResult = ModelValidationResult.valid(validationStates, operationResult),
            updatedListAdapter = updatedListState
        )
    }
}

