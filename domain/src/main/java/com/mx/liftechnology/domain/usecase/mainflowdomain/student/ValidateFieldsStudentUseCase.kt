/**
 * @file Proporciona el caso de uso para la validación de campos del formulario de registro de estudiantes.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.student

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelRegex
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Interfaz para el caso de uso que valida los campos del formulario de registro de estudiantes.
 * Define los contratos para las diferentes validaciones de los datos del estudiante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsStudentUseCase {
    /**
     * Valida el campo del nombre.
     * @param name El nombre a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateName(name: String?): ModelStateOutFieldText

    /**
     * Valida el campo del apellido paterno.
     * @param lastName El apellido a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateLastName(lastName: String?): ModelStateOutFieldText

    /**
     * Valida el campo del apellido materno.
     * @param secondLastName El apellido a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateSecondLastName(secondLastName: String?): ModelStateOutFieldText

    /**
     * Valida el campo de la CURP.
     * @param curp La CURP a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateCurp(curp: String?): ModelStateOutFieldText

    /**
     * Valida el campo de la fecha de nacimiento.
     * @param birthday La fecha a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateBirthday(birthday: String?): ModelStateOutFieldText

    /**
     * Valida el campo del número de teléfono.
     * @param phoneNumber El número a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validatePhoneNumber(phoneNumber: String?): ModelStateOutFieldText
}

/**
 * Implementación de [ValidateFieldsStudentUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsStudentUseCaseImp : ValidateFieldsStudentUseCase {
    /**
     * {@inheritDoc}
     */
    override fun validateName(name: String?): ModelStateOutFieldText {
        return when {
            name.isNullOrEmpty() -> name.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> name.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateLastName(lastName: String?): ModelStateOutFieldText {
        return when {
            lastName.isNullOrEmpty() -> lastName.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> lastName.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateSecondLastName(secondLastName: String?): ModelStateOutFieldText {
        return when {
            secondLastName.isNullOrEmpty() -> secondLastName.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> secondLastName.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateCurp(curp: String?): ModelStateOutFieldText {
        return when {
            curp.isNullOrEmpty() -> curp.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            curp.isNotValid() -> curp.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_CURP_FORMAT_MISTAKE
            )
            else -> curp.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun String.isNotValid(): Boolean {
        val regex = ModelRegex.CURP
        return regex.matches(this)
    }

    /**
     * {@inheritDoc}
     */
    override fun validatePhoneNumber(phoneNumber: String?): ModelStateOutFieldText {
        return when {
            phoneNumber.isNullOrEmpty() -> phoneNumber.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            !phoneNumber.isValidPhoneNumber() -> phoneNumber.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_PHONE_NUMBER_FORMAT_MISTAKE
            )
            else -> phoneNumber.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun String.isValidPhoneNumber(): Boolean = ModelRegex.PHONE_NUMBER.matches(this)

    /**
     * {@inheritDoc}
     */
    override fun validateBirthday(birthday: String?): ModelStateOutFieldText {
        return when {
            birthday.isNullOrEmpty() -> birthday.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> birthday.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}