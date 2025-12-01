package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.data.repository.formativeField.DeleteFormativeFieldRepository
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult

class DeleteFormativeFieldsUseCase(
    private val deleteFormativeFieldRepository: DeleteFormativeFieldRepository
) {

    suspend operator fun invoke (fieldId: Int): ModelResult<String, ModelError>{
        return runCatching { deleteFormativeFieldRepository.delete(fieldId) }.fold(
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