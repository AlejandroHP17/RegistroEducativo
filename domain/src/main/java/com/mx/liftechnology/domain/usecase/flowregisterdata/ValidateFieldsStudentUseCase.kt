package com.mx.liftechnology.domain.usecase.flowregisterdata

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState

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
        val regex = """^[A-Z]{4}\d{6}[HM][A-Z]{5}[A-Z\d]\d$""".toRegex()
        return !regex.matches(this)
    }


    override fun validatePhoneNumber(phoneNumber: String?): ModelState<Int, Int> {
        return when {
            phoneNumber.isNullOrEmpty()-> ErrorState(ModelCodeError.ET_EMPTY)
            phoneNumber.validPhoneNumber() -> ErrorState(ModelCodeError.ET_MISTAKE_PHONE_NUMBER)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    private fun String.validPhoneNumber():Boolean{
        val regex = """^\d{10}$""".toRegex()
        return regex.matches(this)
    }


    override fun validateBirthday(birthday: String?): ModelState<Int, Int> {
        return when{
            birthday.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

}

