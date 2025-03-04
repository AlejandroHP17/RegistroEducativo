package com.mx.liftechnology.domain.usecase.flowdata.school

import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

interface ValidateFieldsRegisterUseCase {
    fun validateGrade(grade: Int?): ModelState<String, String>
    fun validateGroup(group: String?): ModelState<String, String>
    fun validateCycle(cycle: Int?): ModelState<String, String>
    fun validatePeriod(period: Int?): ModelState<String, String>
    fun validateAdapter(adapterPeriods: MutableList<ModelDatePeriodDomain>?): ModelState<String, String>
}

class ValidateFieldsRegisterUseCaseImp : ValidateFieldsRegisterUseCase {

    /** validateGrade
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateGrade(grade: Int?): ModelState<String, String> {
        return when (grade) {
            null -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            0 -> ErrorUserState(ModelCodeInputs.ET_MISTAKE_FORMAT)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateGroup
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateGroup(group: String?): ModelState<String, String> {
        return when {
            group.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateCycle
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCycle(cycle: Int?): ModelState<String, String> {
        return when (cycle) {
            null -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            0 -> ErrorUserState(ModelCodeInputs.ET_MISTAKE_FORMAT)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validatePeriod
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePeriod(period: Int?): ModelState<String, String> {
        return when {
            (period ?: 0) <= 0 -> ErrorUserState(ModelCodeInputs.SP_NOT_OPTION)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateAdapter
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateAdapter(adapterPeriods: MutableList<ModelDatePeriodDomain>?): ModelState<String, String> {
        return when{
            adapterPeriods?.size == 0 -> ErrorUserState(ModelCodeInputs.SP_NOT_OPTION)
            adapterPeriods?.any { it.date?.contains("Parcial", ignoreCase = true) == true }?: true
                -> ErrorUserState(ModelCodeInputs.SP_NOT_OPTION)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

}

