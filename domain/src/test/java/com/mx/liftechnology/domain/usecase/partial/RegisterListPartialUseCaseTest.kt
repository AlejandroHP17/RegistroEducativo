package com.mx.liftechnology.domain.usecase.partial

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain
import com.mx.liftechnology.domain.repository.partial.PartialRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterListPartialUseCase].
 */
class RegisterListPartialUseCaseTest {

    private lateinit var partialRepository: PartialRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: RegisterListPartialUseCase

    @Before
    fun setup() {
        partialRepository = mockk()
        preferenceUseCase = mockk()
        useCase = RegisterListPartialUseCase(partialRepository, preferenceUseCase)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando no hay ciclo escolar o lista vacia`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns null

        val result: ModelResult<List<ListPartialDomain?>, ModelError> = useCase(emptyList())

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando registro exitoso con datos se devuelve SuccessResult`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns 1
        val adapter = listOf(DatePeriodDomain(position = 0, date = mockk(relaxed = true), partialCycleGroup = 10))
        val repoResult = listOf<ListPartialDomain?>(mockk())
        coEvery { partialRepository.register(any(), 1) } returns SuccessResult(repoResult)

        val result: ModelResult<List<ListPartialDomain?>, ModelError> = useCase(adapter)

        assertTrue(result is SuccessResult)
        assertEquals(repoResult, (result as SuccessResult).data)
    }

    @Test
    fun `cuando registro exitoso pero sin datos devuelve NOT_FOUND`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns 1
        val adapter = listOf(DatePeriodDomain(position = 0, date = mockk(relaxed = true), partialCycleGroup = 10))
        coEvery { partialRepository.register(any(), 1) } returns SuccessResult(emptyList())

        val result: ModelResult<List<ListPartialDomain?>, ModelError> = useCase(adapter)

        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }
}
