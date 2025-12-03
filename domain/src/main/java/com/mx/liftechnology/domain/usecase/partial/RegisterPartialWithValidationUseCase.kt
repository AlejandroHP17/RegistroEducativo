package com.mx.liftechnology.domain.usecase.partial

import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsRegisterPartialUseCase

/**
 * Caso de uso que combina la validación de campos de registro de parciales con la ejecución del registro.
 * Encapsula la lógica de negocio de validación + operación, retornando los estados de validación
 * para que el ViewModel pueda actualizar la UI.
 *
 * @property validateFieldsUseCase El caso de uso para validar los campos del formulario.
 * @property registerListPartialUseCase El caso de uso para ejecutar el registro de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterPartialWithValidationUseCase(
    private val validateFieldsUseCase: ValidateFieldsRegisterPartialUseCase,
    private val registerListPartialUseCase: RegisterListPartialUseCase
) {
    /**
     * Valida los campos del formulario de registro de parciales y, si son válidos, ejecuta el registro.
     *
     * @param numberPartials El número de parciales.
     * @param listCalendar La lista de períodos de fechas.
     * @return Un [ModelValidationResult] que contiene:
     * - Los estados de validación de cada campo (para actualizar la UI)
     * - El resultado de la operación de registro (solo si todas las validaciones pasaron)
     * - Un flag que indica si todas las validaciones pasaron
     */
    data class ValidationResult(
        val validationResult: ModelValidationResult<List<ListPartialDomain?>>,
        val updatedListCalendar: List<DatePeriodDomain>?
    )

    suspend operator fun invoke(
        numberPartials: String?,
        listCalendar: List<DatePeriodDomain>?
    ): ValidationResult {
        // 1. Validar todos los campos
        val periodState = validateFieldsUseCase.validatePeriod(numberPartials)
        val listCalendarState = validateFieldsUseCase.validateAdapter(listCalendar)
        val calendarState = validateFieldsUseCase.validateAdapterError(listCalendarState)

        val validationStates = mapOf(
            "numberPartials" to periodState,
            "calendar" to calendarState
        )

        // 2. Verificar si hay errores de validación
        val hasErrors = periodState.isError || calendarState.isError

        // 3. Si hay errores, retornar resultado de validación fallida
        if (hasErrors) {
            return ValidationResult(
                validationResult = ModelValidationResult.invalid(validationStates),
                updatedListCalendar = listCalendarState
            )
        }

        // 4. Si todas las validaciones pasaron, ejecutar la operación
        val operationResult = registerListPartialUseCase.invoke(
            adapterPeriods = listCalendarState ?: emptyList()
        )

        // 5. Retornar resultado con validación exitosa y resultado de la operación
        return ValidationResult(
            validationResult = ModelValidationResult.valid(validationStates, operationResult),
            updatedListCalendar = listCalendarState
        )
    }
}

