package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

interface ValidateFieldsStudentUseCase {
    fun validateName(name: String?): ModelState<String, String>
    fun validateLastName(lastName: String?): ModelState<String, String>
    fun validateSecondLastName(secondLastName: String?): ModelState<String, String>
    fun validateCurp(curp: String?): ModelState<String, String>
    fun validateBirthday(birthday: String?): ModelState<String, String>
    fun validatePhoneNumber(validatePhoneNumber:String?): ModelState<String, String>
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

}

