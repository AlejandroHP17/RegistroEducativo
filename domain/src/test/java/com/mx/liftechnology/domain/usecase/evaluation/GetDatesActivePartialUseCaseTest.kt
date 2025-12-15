package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.core.preference.PreferenceUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetDatesActivePartialUseCase].
 */
class GetDatesActivePartialUseCaseTest {

    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: GetDatesActivePartialUseCase

    @Before
    fun setup() {
        preferenceUseCase = mockk()
        useCase = GetDatesActivePartialUseCase(preferenceUseCase)
    }

    @Test
    fun `retorna rango de fechas cuando existe en preferencias`() = runTest {
        every { preferenceUseCase.getRangeDatesPartial() } returns "2024-01-01/2024-02-28"

        val result = useCase()

        assertEquals("2024-01-01/2024-02-28", result)
    }

    @Test
    fun `retorna null cuando no existe rango de fechas`() = runTest {
        every { preferenceUseCase.getRangeDatesPartial() } returns null

        val result = useCase()

        assertEquals(null, result)
    }
}
