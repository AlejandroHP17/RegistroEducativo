package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.data.repository.formativeField.DeleteFormativeFieldRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

class DeleteFormativeFieldsUseCase(
    private val deleteFormativeFieldRepository: DeleteFormativeFieldRepository
) {

    suspend operator fun invoke (fieldId: Int): ModelResult<String, Error>{
        return runCatching { deleteFormativeFieldRepository.executeFormativeFieldsStudent(fieldId) }.fold(
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