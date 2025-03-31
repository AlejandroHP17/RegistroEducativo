package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState

interface ValidateFieldsRegisterUseCase {
    fun validateGrade(grade: Int?): ModelState<String, String>
    fun validateGradeCompose(grade: Int?): ModelStateOutFieldText
    fun validateGroup(group: String?): ModelState<String, String>
    fun validateGroupCompose(group: String?): ModelStateOutFieldText
    fun validateCycle(cycle: Int?): ModelState<String, String>
    fun validateCycleCompose(cycle: Int?): ModelStateOutFieldText
    fun validatePeriod(period: Int?): ModelState<String, String>
    fun validatePeriodCompose(period: Int?): ModelStateOutFieldText
    fun validateAdapter(adapterPeriods: List<ModelDatePeriodDomain>): ModelState<String, String>
    fun validateAdapterCompose(adapterPeriods: List<ModelDatePeriodDomain>): ModelStateOutFieldText
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
    override fun validateGradeCompose(grade: Int?): ModelStateOutFieldText{
        return when (grade) {
            null -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY)
            0 -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_MISTAKE_FORMAT)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
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
    override fun validateGroupCompose(group: String?): ModelStateOutFieldText {
        return when {
            group.isNullOrEmpty() -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
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
    override fun validateCycleCompose(cycle: Int?): ModelStateOutFieldText {
        return when (cycle) {
            null -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY)
            0 -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_MISTAKE_FORMAT)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
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
    override fun validatePeriodCompose(period: Int?): ModelStateOutFieldText {
        return when {
            (period ?: 0) <= 0 -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.SP_NOT_OPTION)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateAdapter
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateAdapter(adapterPeriods: List<ModelDatePeriodDomain>): ModelState<String, String> {
        return when{
            adapterPeriods.isEmpty() -> ErrorUserState(ModelCodeInputs.SP_NOT_OPTION)
            adapterPeriods.any { it.date?.contains("Parcial", ignoreCase = true) == true }?: true
                -> ErrorUserState(ModelCodeInputs.SP_NOT_OPTION)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
    override fun validateAdapterCompose(adapterPeriods: List<ModelDatePeriodDomain>): ModelStateOutFieldText{
        return when{
            adapterPeriods.isEmpty() -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.SP_NOT_OPTION)
            adapterPeriods.any { it.date?.contains("Parcial", ignoreCase = true) == true }?: true
                -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.SP_NOT_OPTION)
            else -> ModelStateOutFieldText(isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

}

