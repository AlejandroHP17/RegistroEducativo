package com.mx.liftechnology.domain.usecase.partial

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.schoolCycle.PartialDomain
import com.mx.liftechnology.domain.repository.partial.PartialRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListPartialUseCase].
 */
class GetListPartialUseCaseTest {

    private lateinit var partialRepository: PartialRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: GetListPartialUseCase

    @Before
    fun setup() {
        partialRepository = mockk()
        preferenceUseCase = mockk()
        useCase = GetListPartialUseCase(partialRepository, preferenceUseCase)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando no hay ciclo escolar`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns null

        val result: ModelResult<MutableList<DatePeriodDomain>?, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando lista resulta vacia retorna EMPTY`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns 1
        coEvery { partialRepository.getList(1) } returns SuccessResult(emptyList())

        val result: ModelResult<MutableList<DatePeriodDomain>?, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `convierte correctamente PartialDomain a DatePeriodDomain`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns 1
        val partials = listOf(
            PartialDomain(partialId = 10, startDate = "2024-01-01", endDate = "2024-02-01")
        )
        coEvery { partialRepository.getList(1) } returns SuccessResult(partials)

        val result: ModelResult<MutableList<DatePeriodDomain>?, ModelError> = useCase()

        assertTrue(result is SuccessResult)
        val list = (result as SuccessResult).data
        assertEquals(1, list?.size)
        val item = list!![0]
        assertEquals(0, item.position)
        assertEquals("2024-01-01 / 2024-02-01", item.date.valueText)
        assertEquals(10, item.partialCycleGroup)
    }
}
