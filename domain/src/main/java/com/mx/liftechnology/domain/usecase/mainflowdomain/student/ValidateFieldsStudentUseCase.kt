package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

interface ValidateFieldsStudentUseCase {
    fun validateName(name: String?): ModelStateOutFieldText
    fun validateLastName(lastName: String?): ModelStateOutFieldText
    fun validateSecondLastName(secondLastName: String?): ModelStateOutFieldText
    fun validateCurp(curp: String?): ModelStateOutFieldText
    fun validateBirthday(birthday: String?): ModelStateOutFieldText
    fun validatePhoneNumber(validatePhoneNumber: String?): ModelStateOutFieldText
}

class ValidateFieldsStudentUseCaseImp : ValidateFieldsStudentUseCase {
    /** validateName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateName(name: String?): ModelStateOutFieldText {
        return when {
            name.isNullOrEmpty() -> name.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> name.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateLastName(lastName: String?): ModelStateOutFieldText {
        return when {
            lastName.isNullOrEmpty() -> lastName.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> lastName.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
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

    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCurp(curp: String?): ModelStateOutFieldText {
        return when {
            curp.isNullOrEmpty() -> curp.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            curp.valid() -> curp.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_CURP_FORMAT_MISTAKE
            )

            else -> curp.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun String.valid(): Boolean {
        val regex = ModelRegex.CURP
        return !regex.matches(this)
    }

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