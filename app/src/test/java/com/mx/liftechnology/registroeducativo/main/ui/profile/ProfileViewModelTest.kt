package com.mx.liftechnology.registroeducativo.main.ui.profile

import com.mx.liftechnology.core.preference.PreferenceUseCase
import org.junit.Test
import io.mockk.mockk
import io.mockk.verify

/**
 * Tests para [ProfileViewModel].
 */
class ProfileViewModelTest {

    @Test
    fun `closeSession llama cleanPreference`() {
        val preferenceUseCase = mockk<PreferenceUseCase>(relaxed = true)
        val viewModel = ProfileViewModel(preferenceUseCase)

        viewModel.closeSession()

        verify(exactly = 1) { preferenceUseCase.cleanPreference() }
    }
}
