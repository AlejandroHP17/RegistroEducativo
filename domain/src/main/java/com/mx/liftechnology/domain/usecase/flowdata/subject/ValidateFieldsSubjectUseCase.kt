package com.mx.liftechnology.domain.usecase.flowdata.subject

import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

interface ValidateFieldsSubjectUseCase {
    fun validateName(nameStudent: String?): ModelState<String, String>
    fun validateListJobs(listJobs: MutableList<ModelFormatSubjectDomain>?): ModelState<String, String>
}

class ValidateFieldsSubjectUseCaseImp : ValidateFieldsSubjectUseCase {
    /** validatePass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateName(nameStudent: String?): ModelState<String, String> {
        return when {
            nameStudent.isNullOrEmpty() -> ErrorUserState(ModelCodeInputs.ET_EMPTY)
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /** Validate Pass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateListJobs(listJobs: MutableList<ModelFormatSubjectDomain>?): ModelState<String, String> {
        return when{
            listJobs?.size == 0 -> ErrorUserState(ModelCodeInputs.SP_NOT_OPTION)
            listJobs?.any { it.name.isNullOrEmpty() || it.percent.isNullOrEmpty() }
                ?: false -> ErrorUserState(ModelCodeInputs.SP_NOT_OPTION)

            !validPercent(listJobs) -> {ErrorUserState(ModelCodeInputs.SP_NOT_JOB)}
            else -> SuccessState(ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    private fun validPercent(listJobs: MutableList<ModelFormatSubjectDomain>?) =
        listJobs?.let { jobs ->
            jobs.all { (it.percent?.toInt() ?: 0) > 0 } && jobs.sumOf { it.percent?.toInt() ?: 0 } == 100
        } ?: false


}

