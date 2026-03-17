package com.mx.liftechnology.domain.usecase.menu

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.schoolCycle.DialogGroupPartialDomain
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests para [SavePartialMenuUseCase].
 */
class SavePartialMenuUseCaseTest {

    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: SavePartialMenuUseCase

    @Before
    fun setup() {
        preferenceUseCase = mockk(relaxed = true)
        useCase = SavePartialMenuUseCase(preferenceUseCase)
    }

    @Test
    fun `cuando lista es nula limpia preferencias y retorna null`() {
        val result = useCase(null)

        assertEquals(null, result)
        verify(exactly = 1) { preferenceUseCase.setRangeDatesPartial(null) }
        verify(exactly = 1) { preferenceUseCase.setIdPartial(-1) }
    }

    @Test
    fun `cuando ninguna fecha contiene la actual selecciona el ultimo`() {
        val list = listOf(
            DialogGroupPartialDomain(false, "P1", 1, "2000-01-01", "2000-01-02"),
            DialogGroupPartialDomain(false, "P2", 2, "2000-02-01", "2000-02-02")
        )

        val result = useCase(list)

        assertEquals(2, result?.partialId)
        verify(exactly = 1) { preferenceUseCase.setIdPartial(2) }
        verify(exactly = 1) { preferenceUseCase.setRangeDatesPartial("2000-02-01/2000-02-02") }
    }
}
