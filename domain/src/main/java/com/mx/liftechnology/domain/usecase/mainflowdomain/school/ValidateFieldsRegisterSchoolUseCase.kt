package com.mx.liftechnology.domain.usecase.mainflowdomain.school

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
    override fun validateGradeCompose(grade: String?): ModelStateOutFieldText{
        return when {
            grade.isNullOrBlank() -> ModelStateOutFieldText(valueText = grade?:"", isError = true,  errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY)
            grade.toIntOrNull() == null -> ModelStateOutFieldText(valueText = grade, isError = true,  errorMessage = ModelCodeInputs.ET_MISTAKE_FORMAT)
            else -> ModelStateOutFieldText(valueText = grade,isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateGroup
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateGroupCompose(group: String?): ModelStateOutFieldText {
        return when {
            group.isNullOrEmpty() -> ModelStateOutFieldText(valueText = group?:"",isError = true,  errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY)
            else -> ModelStateOutFieldText(valueText = group, isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateCycle
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCycleCompose(cycle: String?): ModelStateOutFieldText {
        return when {
            cycle.isNullOrBlank() -> ModelStateOutFieldText(valueText = cycle?:"",isError = true,  errorMessage = ModelCodeInputs.ET_SPINNER_EMPTY)
            cycle.toIntOrNull() == null -> ModelStateOutFieldText(valueText = cycle, isError = true,  errorMessage = ModelCodeInputs.ET_MISTAKE_FORMAT)
            else -> ModelStateOutFieldText(valueText = cycle, isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** validateCct
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateCctCompose(cct: String?): ModelStateOutFieldText {
        return when {
            cct.isNullOrEmpty() -> ModelStateOutFieldText(valueText = cct?:"", isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            cct.length != 10 -> ModelStateOutFieldText(valueText = cct, isError = true,  errorMessage = ModelCodeInputs.ET_NOT_FOUND)
            else -> ModelStateOutFieldText(valueText = cct, isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
}