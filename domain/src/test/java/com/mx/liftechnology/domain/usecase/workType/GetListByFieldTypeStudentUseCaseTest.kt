package com.mx.liftechnology.domain.usecase.workType

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.ByFieldTypeStudentDomain
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListByFieldTypeStudentUseCase].
 */
class GetListByFieldTypeStudentUseCaseTest {

    private lateinit var evaluationRepository: EvaluationRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: GetListByFieldTypeStudentUseCase

    @Before
    fun setup() {
        evaluationRepository = mockk()
        preferenceUseCase = mockk()
        useCase = GetListByFieldTypeStudentUseCase(evaluationRepository, preferenceUseCase)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando no hay campo formativo en preferencias`() = runTest {
        io.mockk.every { preferenceUseCase.getIdFormativeField() } returns null

        val result: ModelResult<ByFieldTypeStudentDomain, ModelError> = useCase(null, null, null)

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando workTypeId es null`() = runTest {
        io.mockk.every { preferenceUseCase.getIdFormativeField() } returns 1

        val result: ModelResult<ByFieldTypeStudentDomain, ModelError> = useCase(null, null, null)

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando repositorio retorna SuccessResult se devuelve SuccessResult`() = runTest {
        io.mockk.every { preferenceUseCase.getIdFormativeField() } returns 5
        val domain = ByFieldTypeStudentDomain(emptyList())
        coEvery { evaluationRepository.getByFieldType(5, 2, "Tarea", "2024-01-10") } returns SuccessResult(domain)

        val result: ModelResult<ByFieldTypeStudentDomain, ModelError> = useCase(2, "Tarea", "2024-01-10")

        assertTrue(result is SuccessResult)
        assertEquals(domain, (result as SuccessResult).data)
    }

    @Test
    fun `cuando ocurre excepcion se retorna UNKNOWN`() = runTest {
        io.mockk.every { preferenceUseCase.getIdFormativeField() } returns 5
        coEvery { evaluationRepository.getByFieldType(any(), any(), any(), any()) } throws RuntimeException("error")

        val result: ModelResult<ByFieldTypeStudentDomain, ModelError> = useCase(2, null, null)

        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNKNOWN, (result as ErrorResult).error)
    }
}
