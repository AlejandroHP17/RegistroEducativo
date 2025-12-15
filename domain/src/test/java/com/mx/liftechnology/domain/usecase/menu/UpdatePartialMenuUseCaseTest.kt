package com.mx.liftechnology.domain.usecase.menu

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.schoolCycle.DialogGroupPartialDomain
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 * Tests para [UpdatePartialMenuUseCase].
 */
class UpdatePartialMenuUseCaseTest {

    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: UpdatePartialMenuUseCase

    @Before
    fun setup() {
        preferenceUseCase = mockk(relaxed = true)
        useCase = UpdatePartialMenuUseCase(preferenceUseCase)
    }

    @Test
    fun `guarda id de parcial y rango de fechas cuando partial no es nulo`() {
        val partial = DialogGroupPartialDomain(
            selected = true,
            namePartial = "Parcial 1",
            partialId = 2,
            startDate = "2024-01-01",
            endDate = "2024-02-01"
        )

        useCase(partial)

        verify(exactly = 1) { preferenceUseCase.setIdPartial(2) }
        verify(exactly = 1) { preferenceUseCase.setRangeDatesPartial("2024-01-01/2024-02-01") }
    }

    @Test
    fun `usa valores por defecto cuando partial es nulo`() {
        useCase(null)

        verify(exactly = 1) { preferenceUseCase.setIdPartial(-1) }
        verify(exactly = 1) { preferenceUseCase.setRangeDatesPartial("null/null") }
    }
}
