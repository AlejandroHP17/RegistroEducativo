package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Interface for validating fields in the assignment form.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsAssignmentUseCase {
    /**
     * Validates the job name field.
     * @param nameJob The job name to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateNameJob(nameJob: String?): ModelStateOutFieldText

    /**
     * Validates the assignment name field.
     * @param nameAssignment The assignment name to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateNameAssignment(nameAssignment: String?): ModelStateOutFieldText

    /**
     * Validates the date field.
     * @param date The date to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateDate(date: String?): ModelStateOutFieldText
}

/**
 * Implementation of [ValidateFieldsAssignmentUseCase].
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