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
            val isNameInvalid = item.name.valueText.isNullOrEmpty()
            val isPercentInvalid = item.percent.valueText.isNullOrEmpty()

            item.copy(
                name = item.name.copy(
                    valueText = item.name.valueText,
                    isError = isNameInvalid,
                    errorMessage = if (isNameInvalid) ModelCodeInputs.SP_NOT_OPTION else ""
                ),
                percent = item.percent.copy(
                    valueText = item.percent.valueText,
                    isError = isPercentInvalid,
                    errorMessage = if (isPercentInvalid) ModelCodeInputs.SP_NOT_JOB else ""
                )
            )
        }?.toMutableList()
    }

    override fun validPercentCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?) : ModelStateOutFieldText{
        return when {
            listJobs?.let { jobs ->
                jobs.all { (it.percent.valueText.toIntOrNull() ?: 0) > 0 } && jobs.sumOf { it.percent.valueText.toIntOrNull() ?: 0 } == 100
            } ?: false -> ModelStateOutFieldText(valueText = listJobs?.size.toString(), isError = false,  errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
            else -> ModelStateOutFieldText(valueText = listJobs?.size.toString(), isError = true,  errorMessage = ModelCodeInputs.SP_NOT)
        }
    }
}

