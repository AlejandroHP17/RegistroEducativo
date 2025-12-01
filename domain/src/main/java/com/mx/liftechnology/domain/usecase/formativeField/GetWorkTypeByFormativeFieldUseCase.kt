package com.mx.liftechnology.domain.usecase.formativeField


import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeByFormativeField
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeByFormativeFieldsRepository
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult

class GetWorkTypeByFormativeFieldUseCase (
    private val getWorkTypeByFormativeFieldsRepository: GetWorkTypeByFormativeFieldsRepository,
    private val preference : PreferenceUseCase
){
    suspend operator fun invoke(): ModelResult<ModelWorkTypeByFormativeField, ModelError> {
        val formativeFieldId =
            preference.getIdFormativeField() ?: return ErrorResult(
                LocalModelError.USER_INCOMPLETE_DATA
            )

        return runCatching { getWorkTypeByFormativeFieldsRepository.executeGetWorkTyperByFormativeFields(formativeFieldId = formativeFieldId) }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        SuccessResult(result.data)
                    }

                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN) }
        )
    }
}