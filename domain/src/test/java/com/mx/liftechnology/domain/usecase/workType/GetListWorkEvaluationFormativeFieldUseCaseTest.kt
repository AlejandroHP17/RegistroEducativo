package com.mx.liftechnology.domain.usecase.workType

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListWorkEvaluationFormativeFieldUseCase].
 */
class GetListWorkEvaluationFormativeFieldUseCaseTest {

    private lateinit var evaluationRepository: EvaluationRepository
    private lateinit var useCase: GetListWorkEvaluationFormativeFieldUseCase

    @Before
    fun setup() {
        evaluationRepository = mockk()
        useCase = GetListWorkEvaluationFormativeFieldUseCase(evaluationRepository, mockk())
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando formativeFieldId es null`() = runTest {
        val result: ModelResult<WorkTypeFormativeFieldDomain, ModelError> = useCase(null)

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando repositorio retorna SuccessResult se devuelve SuccessResult`() = runTest {
        val domain = WorkTypeFormativeFieldDomain(emptyList())
        coEvery { evaluationRepository.getListWorkTypeFormativeField(3) } returns SuccessResult(domain)

        val result: ModelResult<WorkTypeFormativeFieldDomain, ModelError> = useCase(3)

        assertTrue(result is SuccessResult)
        assertEquals(domain, (result as SuccessResult).data)
    }

    @Test
    fun `cuando ocurre excepcion se retorna UNKNOWN`() = runTest {
        coEvery { evaluationRepository.getListWorkTypeFormativeField(any()) } throws RuntimeException("error")

        val result: ModelResult<WorkTypeFormativeFieldDomain, ModelError> = useCase(3)

        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNKNOWN, (result as ErrorResult).error)
    }
}
