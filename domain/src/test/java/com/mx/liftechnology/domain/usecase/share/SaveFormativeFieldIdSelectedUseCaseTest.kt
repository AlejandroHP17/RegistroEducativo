package com.mx.liftechnology.domain.usecase.share

import com.mx.liftechnology.core.preference.PreferenceUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 * Tests para [SaveFormativeFieldIdSelectedUseCase].
 */
class SaveFormativeFieldIdSelectedUseCaseTest {

    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: SaveFormativeFieldIdSelectedUseCase

    @Before
    fun setup() {
        preferenceUseCase = mockk(relaxed = true)
        useCase = SaveFormativeFieldIdSelectedUseCase(preferenceUseCase)
    }

    @Test
    fun `guarda el id proporcionado cuando no es nulo`() {
        useCase(5)

        verify(exactly = 1) { preferenceUseCase.setIdFormativeField(5) }
    }

    @Test
    fun `guarda menos uno cuando el id es nulo`() {
        useCase(null)

        verify(exactly = 1) { preferenceUseCase.setIdFormativeField(-1) }
    }
}
