package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeByFormativeField
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeByFormativeFieldsRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

class GetWorkTypeByFormativeField (
    private val getWorkTypeByFormativeFieldsRepository: GetWorkTypeByFormativeFieldsRepository,
    private val preference : PreferenceUseCase
){
    suspend operator fun invoke(): ModelResult<ModelWorkTypeByFormativeField, Error> {
        val formativeFieldId = preference.getPreferenceInt(ModelPreference.ID_FORMATIVE_FIELD)

        if(formativeFieldId == null) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
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
            onFailure = { ErrorResult(NetworkError.UNKNOWN) }
        )
    }
}