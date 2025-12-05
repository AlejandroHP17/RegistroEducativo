package com.mx.liftechnology.core.network.util

import com.mx.liftechnology.core.preference.PreferenceUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para [TokenProvider].
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
class TokenProviderTest {

    private lateinit var mockPreferenceUseCase: PreferenceUseCase
    private lateinit var tokenProvider: TokenProvider

    @Before
    fun setup() {
        mockPreferenceUseCase = mockk(relaxed = true)
        tokenProvider = TokenProvider(mockPreferenceUseCase)
    }

    @Test
    fun `getToken returns access token from preferences`() {
        // Given
        val expectedToken = "test_access_token"
        every { mockPreferenceUseCase.getAccessToken() } returns expectedToken

        // When
        val result = tokenProvider.getToken()

        // Then
        assertEquals(expectedToken, result)
        verify { mockPreferenceUseCase.getAccessToken() }
    }

    @Test
    fun `getToken returns null when no token is stored`() {
        // Given
        every { mockPreferenceUseCase.getAccessToken() } returns null

        // When
        val result = tokenProvider.getToken()

        // Then
        assertNull(result)
        verify { mockPreferenceUseCase.getAccessToken() }
    }

    @Test
    fun `getRefreshToken returns refresh token from preferences`() {
        // Given
        val expectedRefreshToken = "test_refresh_token"
        every { mockPreferenceUseCase.getRefreshToken() } returns expectedRefreshToken

        // When
        val result = tokenProvider.getRefreshToken()

        // Then
        assertEquals(expectedRefreshToken, result)
        verify { mockPreferenceUseCase.getRefreshToken() }
    }

    @Test
    fun `getRefreshToken returns null when no refresh token is stored`() {
        // Given
        every { mockPreferenceUseCase.getRefreshToken() } returns null

        // When
        val result = tokenProvider.getRefreshToken()

        // Then
        assertNull(result)
        verify { mockPreferenceUseCase.getRefreshToken() }
    }

    @Test
    fun `saveNewToken saves token when not null`() {
        // Given
        val newToken = "new_access_token"
        every { mockPreferenceUseCase.setAccessToken(any()) } returns Unit

        // When
        tokenProvider.saveNewToken(newToken)

        // Then
        verify { mockPreferenceUseCase.setAccessToken(newToken) }
    }

    @Test
    fun `saveNewToken does not save when token is null`() {
        // When
        tokenProvider.saveNewToken(null)

        // Then
        verify(exactly = 0) { mockPreferenceUseCase.setAccessToken(any()) }
    }

    @Test
    fun `closeSession cleans all preferences`() {
        // Given
        every { mockPreferenceUseCase.cleanPreference() } returns true

        // When
        tokenProvider.closeSession()

        // Then
        verify { mockPreferenceUseCase.cleanPreference() }
    }
}
