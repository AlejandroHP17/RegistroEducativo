package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods

interface ValidateFieldsSubjectUseCase {
    fun validateNameCompose(nameStudent: String?): ModelStateOutFieldText
    fun validateOptionCompose(option: String?): ModelStateOutFieldText
    fun validateListJobsCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): MutableList<ModelSpinnersWorkMethods>?
    fun validPercentCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): ModelStateOutFieldText
}

class ValidateFieldsSubjectUseCaseImp : ValidateFieldsSubjectUseCase {
    /** validatePass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateNameCompose(nameSubject: String?): ModelStateOutFieldText {
        return when {
            nameSubject.isNullOrEmpty() -> ModelStateOutFieldText(valueText = nameSubject?:"", isError = true,  errorMessage = ModelCodeInputs.ET_EMPTY)
            else -> ModelStateOutFieldText(valueText = nameSubject, isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }
    override fun validateOptionCompose(option: String?): ModelStateOutFieldText {
        return when {
            option.isNullOrEmpty() -> ModelStateOutFieldText(valueText = option?:"", isError = true,  errorMessage = ModelCodeInputs.SP_NOT_JOB)
            else -> ModelStateOutFieldText(valueText = option, isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** Validate Pass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateListJobsCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): MutableList<ModelSpinnersWorkMethods>? {
        return listJobs?.map { item ->
            val isNameInvalid = item.name.isNullOrEmpty()
            val isPercentInvalid = item.percent.isNullOrEmpty()

            item.copy(
                isErrorName = item.isErrorName.copy(
                    valueText = item.name?:"",
                    isError = isNameInvalid,
                    errorMessage = if (isNameInvalid) ModelCodeInputs.SP_NOT_OPTION else ""
                ),
                isErrorPercent = item.isErrorPercent.copy(
                    valueText = item.percent?:"",
                    isError = isPercentInvalid,
                    errorMessage = if (isPercentInvalid) ModelCodeInputs.SP_NOT_JOB else ""
                )
            )
        }?.toMutableList()
    }

    override fun validPercentCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?) : ModelStateOutFieldText{
        return when {
            listJobs?.let { jobs ->
                jobs.all { (it.percent?.toIntOrNull() ?: 0) > 0 } && jobs.sumOf { it.percent?.toIntOrNull() ?: 0 } == 100
            } ?: false -> ModelStateOutFieldText( isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
            else -> ModelStateOutFieldText(isError = true,  errorMessage = ModelCodeInputs.SP_NOT)
        }
    }
}

