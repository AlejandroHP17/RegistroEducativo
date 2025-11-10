package com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields

import com.mx.liftechnology.data.repository.flowMain.formativeFields.DeleteFormativeFieldsRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

class DeleteFormativeFieldsUseCase(
    private val deleteFormativeFieldsRepository: DeleteFormativeFieldsRepository
) {

    suspend operator fun invoke (fieldId: Int): ModelResult<String, Error>{
        return runCatching { deleteFormativeFieldsRepository.executeFormativeFieldsStudent(fieldId) }.fold(
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