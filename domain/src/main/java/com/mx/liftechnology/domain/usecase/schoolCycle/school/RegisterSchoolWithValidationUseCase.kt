package com.mx.liftechnology.domain.usecase.schoolCycle.school

import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain

/**
 * Caso de uso que combina la validación de campos de registro de escuela con la ejecución del registro.
 * Encapsula la lógica de negocio de validación + operación, retornando los estados de validación
 * para que el ViewModel pueda actualizar la UI.
 *
 * @property validateFieldsUseCase El caso de uso para validar los campos del formulario.
 * @property registerCycleSchoolUseCase El caso de uso para ejecutar el registro de escuela.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSchoolWithValidationUseCase(
    private val validateFieldsUseCase: ValidateFieldsRegisterSchoolUseCase,
    private val registerCycleSchoolUseCase: RegisterCycleSchoolUseCase
) {
    /**
     * Valida los campos del formulario de registro de escuela y, si son válidos, ejecuta el registro.
     *
     * @param cct La Clave de Centro de Trabajo.
     * @param type El tipo de ciclo (Anual, Semestral, etc).
     * @param grade El grado.
     * @param group El grupo.
     * @param cycle El ciclo.
     * @param labelCycle La etiqueta del ciclo.
     * @param schoolId El ID de la escuela.
     * @param periodCatalogId El ID del catálogo de períodos.
     * @param shiftName El nombre del turno.
     * @return Un [ModelValidationResult] que contiene:
     * - Los estados de validación de cada campo (para actualizar la UI)
     * - El resultado de la operación de registro (solo si todas las validaciones pasaron)
     * - Un flag que indica si todas las validaciones pasaron
     */
    suspend operator fun invoke(
        cct: String?,
        type: String?,
        grade: String?,
        group: String?,
        cycle: String?,
        labelCycle: String?,
        schoolId: Int,
        periodCatalogId: Int,
        shiftName: String
    ): ModelValidationResult<RegisterSchoolCycleDomain> {
        // 1. Validar todos los campos
        val cctState = validateFieldsUseCase.validateCctCompose(cct)
        val typeState = validateFieldsUseCase.validateTypeCompose(type)
        val gradeState = validateFieldsUseCase.validateGradeCompose(grade)
        val groupState = validateFieldsUseCase.validateGroupCompose(group)
        val cycleState = validateFieldsUseCase.validateCycleCompose(cycle)
        val labelCycleState = validateFieldsUseCase.validateLabelCycleCompose(labelCycle)

        val validationStates = mapOf(
            "cct" to cctState,
            "type" to typeState,
            "grade" to gradeState,
            "group" to groupState,
            "cycle" to cycleState,
            "labelCycle" to labelCycleState
        )

        // 2. Verificar si hay errores de validación
        val hasErrors = cctState.isError || typeState.isError || gradeState.isError ||
                groupState.isError || cycleState.isError || labelCycleState.isError

        // 3. Si hay errores, retornar resultado de validación fallida
        if (hasErrors) {
            return ModelValidationResult.invalid(validationStates)
        }

        // 4. Si todas las validaciones pasaron, ejecutar la operación
        val operationResult = registerCycleSchoolUseCase.invoke(
            schoolId = schoolId,
            periodCatalogId = periodCatalogId,
            cct = cct ?: "",
            grade = grade?.toIntOrNull() ?: 0,
            group = group ?: "",
            cycle = cycle?.toIntOrNull() ?: 0,
            shiftName = shiftName,
            labelCycleState = labelCycle ?: ""
        )

        // 5. Retornar resultado con validación exitosa y resultado de la operación
        return ModelValidationResult.valid(validationStates, operationResult)
    }
}

