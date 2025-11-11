/**
 * @file Define el caso de uso para validar los campos del formulario de registro de escuela.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.schoolCycle.school

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText

/**
 * Interfaz para el caso de uso que valida los campos del formulario de registro de escuela.
 * Define los contratos para validar el grado, el grupo, el ciclo y la CCT.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsRegisterSchoolUseCase {
    /**
     * Valida el campo del grado.
     * @param type El tipo a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateTypeCompose(type: String?): ModelStateOutFieldText

    /**
     * Valida el campo del grado.
     * @param grade El grado a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateGradeCompose(grade: String?): ModelStateOutFieldText

    /**
     * Valida el campo del grupo.
     * @param group El grupo a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateGroupCompose(group: String?): ModelStateOutFieldText

    /**
     * Valida el campo del ciclo.
     * @param cycle El ciclo a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateCycleCompose(cycle: String?): ModelStateOutFieldText

    /**
     * Valida el campo de la CCT.
     * @param cct La CCT a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateCctCompose(cct: String?): ModelStateOutFieldText
}

/**
 * Implementación de [ValidateFieldsRegisterSchoolUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsRegisterSchoolUseCaseImp : ValidateFieldsRegisterSchoolUseCase {

    /**
     * {@inheritDoc}
     */
    override fun validateTypeCompose(type: String?): ModelStateOutFieldText {
        return when {
            type.isNullOrEmpty() -> type.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY
            )

            else -> type.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateGradeCompose(grade: String?): ModelStateOutFieldText {
        return when {
            grade.isNullOrBlank() -> grade.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY
            )

            grade.toIntOrNull() == null -> grade.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_MISTAKE_FORMAT
            )

            else -> grade.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateGroupCompose(group: String?): ModelStateOutFieldText {
        return when {
            group.isNullOrEmpty() -> group.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY
            )

            else -> group.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateCycleCompose(cycle: String?): ModelStateOutFieldText {
        return when {
            cycle.isNullOrBlank() -> cycle.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY
            )

            cycle.toIntOrNull() == null -> cycle.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_MISTAKE_FORMAT
            )

            else -> cycle.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateCctCompose(cct: String?): ModelStateOutFieldText {
        return when {
            cct.isNullOrEmpty() -> cct.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            cct.length != 10 -> cct.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_NOT_FOUND
            )

            else -> cct.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}