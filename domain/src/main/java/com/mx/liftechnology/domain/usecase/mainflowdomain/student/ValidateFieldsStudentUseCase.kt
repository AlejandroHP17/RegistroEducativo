package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState

interface ValidateFieldsStudentUseCase {
    fun validateName(name: String?): ModelState<String, String>
    fun validateNameCompose(name: String?): ModelStateOutFieldText
    fun validateLastName(lastName: String?): ModelState<String, String>
    fun validateLastNameCompose(lastName: String?): ModelStateOutFieldText
    fun validateSecondLastName(secondLastName: String?): ModelState<String, String>
    fun validateSecondLastNameCompose(secondLastName: String?):ModelStateOutFieldText
    fun validateCurp(curp: String?): ModelState<String, String>
    fun validateCurpCompose(curp: String?): ModelStateOutFieldText
    fun validateBirthday(birthday: String?): ModelState<String, String>
    fun validateBirthdayCompose(birthday: String?): ModelStateOutFieldText
    fun validatePhoneNumber(validatePhoneNumber:String?): ModelState<String, String>
    fun validatePhoneNumberCompose(validatePhoneNumber:String?): ModelStateOutFieldText
}

class ValidateFieldsStudentUseCaseImp : ValidateFieldsStudentUseCase {
    /** validateName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateName(name: String?): ModelState<String, String> {
        return when {
            name.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
    override fun validateNameCompose(name: String?):ModelStateOutFieldText {
        return when {
            name.isNullOrEmpty() -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }


    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateLastName(lastName: String?): ModelState<String, String> {
        return when {
            lastName.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
    override fun validateLastNameCompose(lastName: String?): ModelStateOutFieldText{
        return when {
            lastName.isNullOrEmpty() -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }


    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateSecondLastName(secondLastName: String?): ModelState<String, String> {
        return when {
            secondLastName.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
    override fun validateSecondLastNameCompose(secondLastName: String?): ModelStateOutFieldText {
        return when {
            secondLastName.isNullOrEmpty() -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCurp(curp: String?): ModelState<String, String> {
        return when {
            curp.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            curp.valid() -> ErrorUserState(ModelCodeInputs.ET_CURP_FORMAT_MISTAKE)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
    override fun validateCurpCompose(curp: String?): ModelStateOutFieldText {
        return when {
            curp.isNullOrEmpty() -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            curp.valid() -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_CURP_FORMAT_MISTAKE)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun String.valid():Boolean{
        val regex = ModelRegex.CURP
        return !regex.matches(this)
    }


    override fun validatePhoneNumber(number: String?): ModelState<String, String> {
        return when {
            number.isNullOrEmpty()-> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            number.validPhoneNumber() -> ErrorUserState(ModelCodeInputs.ET_PHONE_NUMBER_FORMAT_MISTAKE)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
    override fun validatePhoneNumberCompose(number: String?): ModelStateOutFieldText{
        return when {
            number.isNullOrEmpty()-> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            number.validPhoneNumber() -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_PHONE_NUMBER_FORMAT_MISTAKE)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun String.validPhoneNumber():Boolean{
        val regex = ModelRegex.PHONE_NUMBER
        return !regex.matches(this)
    }


    override fun validateBirthday(birthday: String?): ModelState<String, String> {
        return when{
            birthday.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
    override fun validateBirthdayCompose(birthday: String?): ModelStateOutFieldText{
        return when{
            birthday.isNullOrEmpty() -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else ->  ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

}

