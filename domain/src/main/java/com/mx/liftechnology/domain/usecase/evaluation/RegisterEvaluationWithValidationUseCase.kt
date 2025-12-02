package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult

/**
 * Caso de uso que combina la validación de campos de registro de evaluación con la ejecución del registro.
 * Encapsula la lógica de negocio de validación + operación, retornando los estados de validación
 * para que el ViewModel pueda actualizar la UI.
 *
 * @property validateFieldsUseCase El caso de uso para validar los campos del formulario.
 * @property registerWorkTypeEvaluationsUseCase El caso de uso para ejecutar el registro de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterEvaluationWithValidationUseCase(
    private val validateFieldsUseCase: ValidateFieldsEvaluationUseCase,
    private val registerWorkTypeEvaluationsUseCase: RegisterWorkTypeEvaluationsUseCase
) {
    /**
     * Valida los campos del formulario de registro de evaluación y, si son válidos, ejecuta el registro.
     *
     * @param nameJob El nombre del trabajo.
     * @param nameAssignment El nombre de la asignación.
     * @param date La fecha de la evaluación.
     * @param workTypeId El ID del tipo de trabajo.
     * @param studentListUI La lista de estudiantes con sus calificaciones.
     * @return Un [ModelValidationResult] que contiene:
     * - Los estados de validación de cada campo (para actualizar la UI)
     * - El resultado de la operación de registro (solo si todas las validaciones pasaron)
     * - Un flag que indica si todas las validaciones pasaron
     */
    suspend operator fun invoke(
        nameJob: String?,
        nameAssignment: String?,
        date: String?,
        workTypeId: Int?,
        studentListUI: List<com.mx.liftechnology.domain.model.evaluation.CardDomain>
    ): ModelValidationResult<Boolean> {
        // 1. Validar todos los campos
        val nameJobState = validateFieldsUseCase.validateNameJob(nameJob)
        val nameAssignmentState = validateFieldsUseCase.validateNameAssignment(nameAssignment)
        val dateState = validateFieldsUseCase.validateDate(date)

        val validationStates = mapOf(
            "nameJob" to nameJobState,
            "nameAssignment" to nameAssignmentState,
            "date" to dateState
        )

        // 2. Verificar si hay errores de validación
        val hasErrors = nameJobState.isError || nameAssignmentState.isError || dateState.isError

        // 3. Si hay errores, retornar resultado de validación fallida
        if (hasErrors) {
            return ModelValidationResult.invalid(validationStates)
        }

        // 4. Si todas las validaciones pasaron, ejecutar la operación
        val operationResult = registerWorkTypeEvaluationsUseCase.invoke(
            workTypeId = workTypeId,
            nameWork = nameJob,
            workDate = date,
            studentListUI = studentListUI
        )

        // 5. Retornar resultado con validación exitosa y resultado de la operación
        return ModelValidationResult.valid(validationStates, operationResult)
    }
}

