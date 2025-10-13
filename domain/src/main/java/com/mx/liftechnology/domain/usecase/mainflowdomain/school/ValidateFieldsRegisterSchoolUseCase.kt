package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Interface for validating fields in the school registration form.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsRegisterSchoolUseCase {
    /**
     * Validates the grade field.
     * @param grade The grade to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateGradeCompose(grade: String?): ModelStateOutFieldText

    /**
     * Validates the group field.
     * @param group The group to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateGroupCompose(group: String?): ModelStateOutFieldText

    /**
     * Validates the cycle field.
     * @param cycle The cycle to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateCycleCompose(cycle: String?): ModelStateOutFieldText

    /**
     * Validates the CCT field.
     * @param cct The CCT to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateCctCompose(cct: String?): ModelStateOutFieldText
}

/**
 * Implementation of [ValidateFieldsRegisterSchoolUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsRegisterSchoolUseCaseImp : ValidateFieldsRegisterSchoolUseCase {

    /**
     * {@inheritDoc}
     */
    override fun validateGradeCompose(grade: String?): ModelStateOutFieldText {
        return when {
            grade.isNullOrBlank() -> grade.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY
            )

            grade.toIntOrNull() == null -> grade.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_MISTAKE_FORMAT
            )

            else -> grade.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateGroupCompose(group: String?): ModelStateOutFieldText {
        return when {
            group.isNullOrEmpty() -> group.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY
            )

            else -> group.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateCycleCompose(cycle: String?): ModelStateOutFieldText {
        return when {
            cycle.isNullOrBlank() -> cycle.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY
            )

            cycle.toIntOrNull() == null -> cycle.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_MISTAKE_FORMAT
            )

            else -> cycle.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateCctCompose(cct: String?): ModelStateOutFieldText {
        return when {
            cct.isNullOrEmpty() -> cct.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            cct.length != 10 -> cct.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_NOT_FOUND
            )

            else -> cct.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}