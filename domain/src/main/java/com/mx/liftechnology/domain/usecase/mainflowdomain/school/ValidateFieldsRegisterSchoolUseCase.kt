package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

interface ValidateFieldsRegisterSchoolUseCase {
    fun validateGradeCompose(grade: String?): ModelStateOutFieldText
    fun validateGroupCompose(group: String?): ModelStateOutFieldText
    fun validateCycleCompose(cycle: String?): ModelStateOutFieldText
    fun validateCctCompose(cct: String?): ModelStateOutFieldText
}

class ValidateFieldsRegisterSchoolUseCaseImp : ValidateFieldsRegisterSchoolUseCase {

    /** validateGrade
     * @author pelkidev
     * @since 1.0.0
     * */
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

    /** validateGroup
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateGroupCompose(group: String?): ModelStateOutFieldText {
        return when {
            group.isNullOrEmpty() -> group.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY
            )

            else -> group.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateCycle
     * @author pelkidev
     * @since 1.0.0
     * */
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

    /** validateCct
     * @author pelkidev
     * @since 1.0.0
     * */
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