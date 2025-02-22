package com.mx.liftechnology.domain.usecase.flowdata.student

import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelCodeSuccess
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

interface ValidateFieldsStudentUseCase {
    fun validateName(name: String?): ModelState<Int, Int>
    fun validateLastName(lastName: String?): ModelState<Int, Int>
    fun validateSecondLastName(secondLastName: String?): ModelState<Int, Int>
    fun validateCurp(curp: String?): ModelState<Int, Int>
    fun validateBirthday(birthday: String?): ModelState<Int, Int>
    fun validatePhoneNumber(validatePhoneNumber:String?): ModelState<Int, Int>
}

class ValidateFieldsStudentUseCaseImp : ValidateFieldsStudentUseCase {
    /** validateName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateName(name: String?): ModelState<Int, Int> {
        return when {
            name.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }


    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateLastName(lastName: String?): ModelState<Int, Int> {
        return when {
            lastName.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }


    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateSecondLastName(secondLastName: String?): ModelState<Int, Int> {
        return when {
            secondLastName.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    /** validateLastName
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCurp(curp: String?): ModelState<Int, Int> {
        return when {
            curp.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            curp.valid() -> ErrorState(ModelCodeError.ET_MISTAKE_CURP)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    private fun String.valid():Boolean{
        val regex = ModelRegex.CURP
        return !regex.matches(this)
    }


    override fun validatePhoneNumber(number: String?): ModelState<Int, Int> {
        return when {
            number.isNullOrEmpty()-> ErrorState(ModelCodeError.ET_EMPTY)
            number.validPhoneNumber() -> ErrorState(ModelCodeError.ET_MISTAKE_PHONE_NUMBER)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    private fun String.validPhoneNumber():Boolean{
        val regex = ModelRegex.PHONE_NUMBER
        return !regex.matches(this)
    }


    override fun validateBirthday(birthday: String?): ModelState<Int, Int> {
        return when{
            birthday.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

}

