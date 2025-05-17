package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

interface ValidateFieldsAssignmentUseCase {
    fun validateNameJob(nameJob: String?): ModelStateOutFieldText
    fun validateNameAssignment(nameAssignment: String?): ModelStateOutFieldText
    fun validateDate(date: String?): ModelStateOutFieldText
}


class ValidateFieldsAssignmentUseCaseImp : ValidateFieldsAssignmentUseCase{
    override fun validateNameJob(nameJob: String?): ModelStateOutFieldText {
        return when {
            nameJob.isNullOrEmpty() -> nameJob.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> nameJob.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    override fun validateNameAssignment(nameAssignment: String?): ModelStateOutFieldText {
        return when {
            nameAssignment.isNullOrEmpty() -> nameAssignment.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> nameAssignment.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    override fun validateDate(date: String?): ModelStateOutFieldText {
        return when {
            date.isNullOrEmpty() -> date.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )
            else -> date.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }


}