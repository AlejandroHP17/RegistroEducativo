package com.mx.liftechnology.domain.usecase.flowdata.subject

import com.mx.liftechnology.domain.model.ModelFormatSubject
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelCodeSuccess
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState

interface ValidateFieldsSubjectUseCase {
    fun validateName(nameStudent: String?): ModelState<Int, Int>
    fun validateListJobs(listJobs: MutableList<ModelFormatSubject>?): ModelState<Int, String>
}

class ValidateFieldsSubjectUseCaseImp : ValidateFieldsSubjectUseCase {
    /** validatePass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateName(nameStudent: String?): ModelState<Int, Int> {
        return when {
            nameStudent.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

    /** Validate Pass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateListJobs(listJobs: MutableList<ModelFormatSubject>?): ModelState<Int, String> {
        return when{
            listJobs?.size == 0 -> ErrorState(ModelCodeError.SP_NOT_JOB)
            listJobs?.any { it.name.isNullOrEmpty() } == true && listJobs.any { it.percent.isNullOrEmpty() }
                -> ErrorState(ModelCodeError.SP_NOT_OPTION)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

}

