/**
 * @file Define el caso de uso para validar los campos del formulario de asignaciones.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Interfaz para el caso de uso que valida los campos del formulario de asignaciones.
 * Define los contratos para validar el nombre del trabajo, el nombre de la asignación y la fecha.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsAssignmentUseCase {
    /**
     * Valida el campo del nombre del trabajo.
     * @param nameJob El nombre a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateNameJob(nameJob: String?): ModelStateOutFieldText

    /**
     * Valida el campo del nombre de la asignación.
     * @param nameAssignment El nombre a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateNameAssignment(nameAssignment: String?): ModelStateOutFieldText

    /**
     * Valida el campo de la fecha.
     * @param date La fecha a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateDate(date: String?): ModelStateOutFieldText
}

/**
 * Implementación de [ValidateFieldsAssignmentUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsAssignmentUseCaseImp : ValidateFieldsAssignmentUseCase{
    /**
     * {@inheritDoc}
     */
    override fun validateNameJob(nameJob: String?): ModelStateOutFieldText {
        return when {
            nameJob.isNullOrEmpty() -> nameJob.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> nameJob.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateNameAssignment(nameAssignment: String?): ModelStateOutFieldText {
        return when {
            nameAssignment.isNullOrEmpty() -> nameAssignment.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> nameAssignment.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateDate(date: String?): ModelStateOutFieldText {
        return when {
            date.isNullOrEmpty() -> date.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> date.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}