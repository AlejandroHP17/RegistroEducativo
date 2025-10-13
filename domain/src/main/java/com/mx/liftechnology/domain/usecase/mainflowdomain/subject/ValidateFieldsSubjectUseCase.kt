package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods

/**
 * Interface for validating fields related to a subject.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsSubjectUseCase {
    /**
     * Validates the subject name.
     * @param nameSubject The name of the subject to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateNameCompose(nameSubject: String?): ModelStateOutFieldText

    /**
     * Validates a selected option.
     * @param option The option to validate.
     * @return A [ModelStateOutFieldText] with the validation result.
     */
    fun validateOptionCompose(option: String?): ModelStateOutFieldText

    /**
     * Validates a list of work methods (jobs).
     * @param listJobs The list of work methods to validate.
     * @return A mutable list with updated validation states, or null.
     */
    fun validateListJobsCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): MutableList<ModelSpinnersWorkMethods>?

    /**
     * Validates that the sum of percentages for all work methods is 100.
     * @param listJobs The list of work methods to validate.
     * @return A [ModelStateOutFieldText] indicating if the sum is correct.
     */
    fun validPercentCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): ModelStateOutFieldText
}

/**
 * Implementation of [ValidateFieldsSubjectUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsSubjectUseCaseImp : ValidateFieldsSubjectUseCase {
    /**
     * {@inheritDoc}
     */
    override fun validateNameCompose(nameSubject: String?): ModelStateOutFieldText {
        return when {
            nameSubject.isNullOrEmpty() -> nameSubject.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> nameSubject.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateOptionCompose(option: String?): ModelStateOutFieldText {
        return when {
            option.isNullOrEmpty() -> option.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.SP_NOT_JOB
            )

            else -> option.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    override fun validPercentCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): ModelStateOutFieldText {
        return when {
            listJobs?.let { jobs ->
                jobs.all {
                    (it.percent.valueText.toIntOrNull() ?: 0) > 0
                } && jobs.sumOf { it.percent.valueText.toIntOrNull() ?: 0 } == 100
            } ?: false -> ModelStateOutFieldText(
                valueText = listJobs?.size.toString(),
                isError = false,
                errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
            )

            else -> ModelStateOutFieldText(
                valueText = listJobs?.size.toString(),
                isError = true,
                errorMessage = ModelCodeInputs.SP_NOT
            )
        }
    }
}
