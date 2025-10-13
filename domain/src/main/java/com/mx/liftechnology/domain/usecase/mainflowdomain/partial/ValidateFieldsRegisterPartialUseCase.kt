package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Interface for validating fields in the partial registration form.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsRegisterPartialUseCase{
    /**
     * Validates the period field.
     * @param period The period to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validatePeriod(period: String?): ModelStateOutFieldText

    /**
     * Validates a list of date periods from the adapter.
     * @param adapterPeriods The list of date periods to validate.
     * @return The list of date periods with updated validation states, or null.
     */
    fun validateAdapter(adapterPeriods: List<ModelDatePeriodDomain>?):  List<ModelDatePeriodDomain>?

    /**
     * Checks if there are any errors in the list of adapter periods.
     * @param adapterPeriods The list of date periods to check.
     * @return A [ModelStateOutFieldText] indicating if there is an error.
     */
    fun validateAdapterError(adapterPeriods: List<ModelDatePeriodDomain>?): ModelStateOutFieldText
}

/**
 * Implementation of [ValidateFieldsRegisterPartialUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsRegisterPartialUseCaseImp : ValidateFieldsRegisterPartialUseCase {
    /**
     * {@inheritDoc}
     */
    override fun validatePeriod(period: String?): ModelStateOutFieldText {
        return when {
            (period?.toIntOrNull() ?: 0) < 1 -> period.stringToModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.SP_NOT_OPTION)
            else -> period.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    override fun validateAdapterError(adapterPeriods: List<ModelDatePeriodDomain>?):ModelStateOutFieldText{
        return if (adapterPeriods?.any { it.date.isError } == true) {
            ModelStateOutFieldText(isError = true, errorMessage = ModelCodeInputs.SP_NOT_OPTION)
        } else {
            ModelStateOutFieldText(isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}
