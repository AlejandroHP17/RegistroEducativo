package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.data.repository.student.DeleteStudentRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult

class DeleteStudentUseCase(
    private val deleteStudentRepository: DeleteStudentRepository
) {

    suspend operator fun invoke (studentId: Int): ModelResult<String, Error>{
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
            onFailure = { ErrorResult(NetworkError.UNKNOWN) }
        )
    }
}