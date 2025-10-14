package com.mx.liftechnology.core.network

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests para [TokenProvider].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class TokenProviderTest {

    private lateinit var tokenProvider: TokenProvider
    private val preferenceUseCase: PreferenceUseCase = mockk()

    @Before
    fun setUp() {
        tokenProvider = TokenProvider(preferenceUseCase)
    }

    @Test
    fun `getToken devuelve el token guardado`() {
        val fakeToken = "fake_token"
        every { preferenceUseCase.getPreferenceString(ModelPreference.ACCESS_TOKEN) } returns fakeToken

        val token = tokenProvider.getToken()

        assertEquals(fakeToken, token)
    }

    @Test
    fun `getToken devuelve nulo si no hay token`() {
        every { preferenceUseCase.getPreferenceString(ModelPreference.ACCESS_TOKEN) } returns null

        val token = tokenProvider.getToken()

        assertEquals(null, token)
    }
}