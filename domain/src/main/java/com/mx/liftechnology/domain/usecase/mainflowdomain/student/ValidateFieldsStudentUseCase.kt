package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

interface ValidateFieldsStudentUseCase {
    fun validateName(name: String?): ModelStateOutFieldText
    fun validateLastName(lastName: String?): ModelStateOutFieldText
    fun validateSecondLastName(secondLastName: String?):ModelStateOutFieldText
    fun validateCurp(curp: String?): ModelStateOutFieldText
    fun validateBirthday(birthday: String?): ModelStateOutFieldText
    fun validatePhoneNumber(validatePhoneNumber:String?): ModelStateOutFieldText
}

class ValidateFieldsStudentUseCaseImp : ValidateFieldsStudentUseCase {
    /** validateName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateName(name: String?):ModelStateOutFieldText {
        return when {
            name.isNullOrEmpty() -> ModelStateOutFieldText( valueText = name?: "", isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else -> ModelStateOutFieldText(valueText = name,isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }


    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateLastName(lastName: String?): ModelStateOutFieldText{
        return when {
            lastName.isNullOrEmpty() -> ModelStateOutFieldText(valueText = lastName?: "",isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else -> ModelStateOutFieldText(valueText = lastName,isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }


    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateSecondLastName(secondLastName: String?): ModelStateOutFieldText {
        return when {
            secondLastName.isNullOrEmpty() -> ModelStateOutFieldText(valueText = secondLastName?: "",isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else -> ModelStateOutFieldText(valueText = secondLastName,isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCurp(curp: String?): ModelStateOutFieldText {
        return when {
            curp.isNullOrEmpty() -> ModelStateOutFieldText(valueText = curp?: "",isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            curp.valid() -> ModelStateOutFieldText(valueText = curp,isError = true,  errorMessage = ModelCodeInputs.ET_CURP_FORMAT_MISTAKE)
            else -> ModelStateOutFieldText(valueText = curp,isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun String.valid():Boolean{
        val regex = ModelRegex.CURP
        return !regex.matches(this)
    }

    override fun validatePhoneNumber(number: String?): ModelStateOutFieldText{
        return when {
            number.isNullOrEmpty()-> ModelStateOutFieldText(valueText = number?: "",isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            number.validPhoneNumber() -> ModelStateOutFieldText(valueText = number,isError = true,  errorMessage = ModelCodeInputs.ET_PHONE_NUMBER_FORMAT_MISTAKE)
            else -> ModelStateOutFieldText(valueText = number,isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun String.validPhoneNumber():Boolean{
        val regex = ModelRegex.PHONE_NUMBER
        return !regex.matches(this)
    }


    override fun validateBirthday(birthday: String?): ModelStateOutFieldText{
        return when{
            birthday.isNullOrEmpty() -> ModelStateOutFieldText(valueText = birthday?: "" ,isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else ->  ModelStateOutFieldText(valueText = birthday, isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

}

