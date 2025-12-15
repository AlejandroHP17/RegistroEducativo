package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [DeleteFormativeFieldsUseCase].
 */
class DeleteFormativeFieldsUseCaseTest {

    private lateinit var formativeFieldRepository: FormativeFieldRepository
    private lateinit var useCase: DeleteFormativeFieldsUseCase

    @Before
    fun setup() {
        formativeFieldRepository = mockk()
        useCase = DeleteFormativeFieldsUseCase(formativeFieldRepository)
    }

    @Test
    fun `delegates to repository and returns its result on success`() = runTest {
        coEvery { formativeFieldRepository.delete(1) } returns SuccessResult("ok")

        val result: ModelResult<String, ModelError> = useCase(1)

        assertTrue(result is SuccessResult)
        assertEquals("ok", (result as SuccessResult).data)
        coVerify(exactly = 1) { formativeFieldRepository.delete(1) }
    }

    @Test
    fun `delegates to repository and returns its error`() = runTest {
        val error = ErrorResult<ModelError>(mockk())
        coEvery { formativeFieldRepository.delete(2) } returns error

        val result: ModelResult<String, ModelError> = useCase(2)

        assertTrue(result is ErrorResult)
        assertEquals(error, result)
    }
}
