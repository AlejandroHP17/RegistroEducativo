package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Interface for validating fields in the student registration form.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsStudentUseCase {
    /**
     * Validates the name field.
     * @param name The name to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateName(name: String?): ModelStateOutFieldText

    /**
     * Validates the last name field.
     * @param lastName The last name to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateLastName(lastName: String?): ModelStateOutFieldText

    /**
     * Validates the second last name field.
     * @param secondLastName The second last name to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateSecondLastName(secondLastName: String?): ModelStateOutFieldText

    /**
     * Validates the CURP field.
     * @param curp The CURP to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateCurp(curp: String?): ModelStateOutFieldText

    /**
     * Validates the birthday field.
     * @param birthday The birthday to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateBirthday(birthday: String?): ModelStateOutFieldText

    /**
     * Validates the phone number field.
     * @param validatePhoneNumber The phone number to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validatePhoneNumber(validatePhoneNumber: String?): ModelStateOutFieldText
}

/**
 * Implementation of [ValidateFieldsStudentUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsStudentUseCaseImp : ValidateFieldsStudentUseCase {
    /**
     * {@inheritDoc}
     */
    override fun validateName(name: String?): ModelStateOutFieldText {
        return when {
            name.isNullOrEmpty() -> name.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> name.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateLastName(lastName: String?): ModelStateOutFieldText {
        return when {
            lastName.isNullOrEmpty() -> lastName.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> lastName.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateSecondLastName(secondLastName: String?): ModelStateOutFieldText {
        return when {
            secondLastName.isNullOrEmpty() -> secondLastName.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> secondLastName.stringToModelStateOutFieldText(
                isError = false,
                errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
            )
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateCurp(curp: String?): ModelStateOutFieldText {
        return when {
            curp.isNullOrEmpty() -> curp.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            curp.isNotValid() -> curp.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_CURP_FORMAT_MISTAKE
            )

            else -> curp.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun String.isNotValid(): Boolean {
        val regex = ModelRegex.CURP
        return regex.matches(this)
    }

    /**
     * {@inheritDoc}
     */
    override fun validatePhoneNumber(number: String?): ModelStateOutFieldText {
        return when {
            number.isNullOrEmpty() -> number.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            number.validPhoneNumber() -> number.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_PHONE_NUMBER_FORMAT_MISTAKE
            )

            else -> number.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun String.validPhoneNumber(): Boolean {
        val regex = ModelRegex.PHONE_NUMBER
        return !regex.matches(this)
    }


    /**
     * {@inheritDoc}
     */
    override fun validateBirthday(birthday: String?): ModelStateOutFieldText {
        return when {
            birthday.isNullOrEmpty() -> birthday.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> birthday.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}