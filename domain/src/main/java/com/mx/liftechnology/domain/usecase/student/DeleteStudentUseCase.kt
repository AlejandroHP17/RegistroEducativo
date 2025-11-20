package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.data.repository.student.DeleteStudentRepository
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult

class DeleteStudentUseCase(
    private val deleteStudentRepository: DeleteStudentRepository
) {

    suspend operator fun invoke (studentId: Int): ModelResult<String, ModelError>{
        return runCatching { deleteStudentRepository.executeDeleteStudent(studentId) }.fold(
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