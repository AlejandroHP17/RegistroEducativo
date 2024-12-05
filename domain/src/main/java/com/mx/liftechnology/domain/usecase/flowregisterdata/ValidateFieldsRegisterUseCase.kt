package com.mx.liftechnology.domain.usecase.flowregisterdata

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelRegex
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.model.ModelDatePeriod

interface ValidateFieldsRegisterUseCase {
    fun validateGrade(grade: String?): ModelState<Int, Int>
    fun validateGroup(group: String?): ModelState<Int, Int>
    fun validateCycle(cycle: String?): ModelState<Int, Int>
    fun validatePeriod(period: Int?): ModelState<Int, String>
    fun validateAdapter(adapterPeriods: MutableList<ModelDatePeriod>?): ModelState<Int, String>
}

class ValidateFieldsRegisterUseCaseImp : ValidateFieldsRegisterUseCase {
    /** validateEmail
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateGrade(grade: String?): ModelState<Int, Int> {
        return when {
            grade.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }


    /** validatePass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateGroup(group: String?): ModelState<Int, Int> {
        return when {
            group.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    /** validatePass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCycle(cycle: String?): ModelState<Int, Int> {
        return when {
            cycle.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    /** Validate Pass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePeriod(period: Int?): ModelState<Int, String> {
        return when {
            (period ?: 0) <= 0 -> ErrorState(ModelCodeError.SP_NOT_OPTION)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    /** Validate Pass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateAdapter(adapterPeriods: MutableList<ModelDatePeriod>?): ModelState<Int, String> {
        return when{
            adapterPeriods?.size == 0 -> ErrorState(ModelCodeError.SP_NOT_OPTION)
            adapterPeriods?.any { it.date?.contains("Parcial", ignoreCase = true) == true }?: true
                -> ErrorState(ModelCodeError.SP_NOT_OPTION)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

}

