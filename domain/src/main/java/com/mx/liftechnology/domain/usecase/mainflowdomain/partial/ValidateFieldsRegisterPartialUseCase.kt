package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

interface ValidateFieldsRegisterPartialUseCase{
    fun validatePeriod(period: String?): ModelStateOutFieldText
    fun validateAdapter(adapterPeriods: List<ModelDatePeriodDomain>?):  List<ModelDatePeriodDomain>?
    fun validateAdapterError(adapterPeriods: List<ModelDatePeriodDomain>?): ModelStateOutFieldText
}

class ValidateFieldsRegisterPartialUseCaseImp : ValidateFieldsRegisterPartialUseCase {
    /** validatePeriod
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validatePeriod(period: String?): ModelStateOutFieldText {
        return when {
            (period?.toIntOrNull() ?: 0) < 1 -> period.stringToModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.SP_NOT_OPTION)
            else -> period.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateAdapter
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateAdapter(adapterPeriods: List<ModelDatePeriodDomain>?): List<ModelDatePeriodDomain>?{
        return adapterPeriods?.map { item ->
            val isDateInvalid = item.date.valueText.isNullOrEmpty()
            item.copy(
                date = item.date.copy(
                    isError = isDateInvalid,
                    errorMessage = if (isDateInvalid) ModelCodeInputs.ET_EMPTY else ""
                )
            )
        }
    }

    override fun validateAdapterError(adapterPeriods: List<ModelDatePeriodDomain>?):ModelStateOutFieldText{
        return if (adapterPeriods?.any { it.date.isError } == true) {
            ModelStateOutFieldText(isError = true, errorMessage = ModelCodeInputs.SP_NOT_OPTION)
        } else {
            ModelStateOutFieldText(isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}

