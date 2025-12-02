/**
 * @file Define el caso de uso para validar los campos del formulario de registro de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.schoolCycle.partial

import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Interfaz para el caso de uso que valida los campos del formulario de registro de parciales.
 * Define los contratos para validar el número de períodos y la lista de fechas de los parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsRegisterPartialUseCase{
    /**
     * Valida el campo del número de períodos.
     * @param period El número de períodos a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validatePeriod(period: String?): ModelStateOutFieldText

    /**
     * Valida una lista de períodos de fechas del adaptador.
     * Marca como erróneos los períodos que no tienen una fecha asignada.
     * @param adapterPeriods La lista de períodos de fechas a validar.
     * @return La lista de períodos con sus estados de validación actualizados, o `null` si la entrada es nula.
     */
    fun validateAdapter(adapterPeriods: List<DatePeriodDomain>?):  List<DatePeriodDomain>?

    /**
     * Comprueba si hay algún error en la lista de períodos del adaptador.
     * @param adapterPeriods La lista de períodos a comprobar.
     * @return Un [ModelStateOutFieldText] que indica si se encontró algún error en la lista.
     */
    fun validateAdapterError(adapterPeriods: List<DatePeriodDomain>?): ModelStateOutFieldText
}

/**
 * Implementación de [ValidateFieldsRegisterPartialUseCase].
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
            (period?.toIntOrNull() ?: 0) < 1 -> period.stringToModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else -> period.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateAdapter(adapterPeriods: List<DatePeriodDomain>?): List<DatePeriodDomain>?{
        return adapterPeriods?.map { item ->
            val isDateInvalid = item.date.valueText.isNullOrEmpty()
            item.copy(
                date = item.date.copy(
                    isError = isDateInvalid,
                    errorMessage = if (isDateInvalid) ModelCodeInputs.SP_NOT_OPTION else ""
                )
            )
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateAdapterError(adapterPeriods: List<DatePeriodDomain>?):ModelStateOutFieldText{
        return if (adapterPeriods?.any { it.date.isError } == true) {
            ModelStateOutFieldText(isError = true, errorMessage = ModelCodeInputs.SP_NOT_OPTION)
        } else {
            ModelStateOutFieldText(isError = false, errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}