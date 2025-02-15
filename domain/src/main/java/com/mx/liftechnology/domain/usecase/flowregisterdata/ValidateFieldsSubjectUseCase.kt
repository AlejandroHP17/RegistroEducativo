package com.mx.liftechnology.domain.usecase.flowregisterdata

import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelCodeSuccess
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.domain.model.ModelFormatSubject

interface ValidateFieldsSubjectUseCase {
    fun validateName(group: String?): ModelState<Int, Int>
    fun validateListJobs(list: MutableList<ModelFormatSubject>?): ModelState<Int, String>
}

class ValidateFieldsSubjectUseCaseImp : ValidateFieldsSubjectUseCase {
    /** validatePass
     * @author pelkidev
     * @since 1.0.0
     * */
    override fun validateName(name: String?): ModelState<Int, Int> {
        return when {
            name.isNullOrEmpty() -> ErrorState(ModelCodeError.ET_EMPTY)
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
            listJobs?.any { it.name.isNullOrEmpty() } == true &&
            listJobs?.any { it.percent.isNullOrEmpty() } == true
                -> ErrorState(ModelCodeError.SP_NOT_OPTION)
            else -> SuccessState(ModelCodeSuccess.ET_FORMAT)
        }
    }

}

