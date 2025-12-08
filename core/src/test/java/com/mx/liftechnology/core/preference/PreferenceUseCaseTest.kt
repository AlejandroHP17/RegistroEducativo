package com.mx.liftechnology.core.preference

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios para [PreferenceUseCase].
 * 
 * @author Pelkidev
 * @version 1.0.0
 */
class PreferenceUseCaseTest {

    private lateinit var mockRepository: PreferenceRepository
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor
    private lateinit var preferenceUseCase: PreferenceUseCase

    @Before
    fun setup() {
        mockRepository = mockk(relaxed = true)
        mockSharedPreferences = mockk(relaxed = true)
        mockEditor = mockk(relaxed = true)
        
        every { mockRepository.getSharedPreferences() } returns mockSharedPreferences
        every { mockSharedPreferences.edit() } returns mockEditor
        every { mockEditor.apply() } returns Unit
        
        preferenceUseCase = PreferenceUseCase(mockRepository)
    }

    // ========== Tests para AccessToken ==========

    @Test
    fun `getAccessToken returns saved value`() {
        // Given
        val expectedToken = "test_access_token"
        every { mockSharedPreferences.getString(PreferenceKeys.ACCESS_TOKEN, null) } returns expectedToken

        // When
        val result = preferenceUseCase.getAccessToken()

        // Then
        assertEquals(expectedToken, result)
    }

    @Test
    fun `getAccessToken returns null when not set`() {
        // Given
        every { mockSharedPreferences.getString(PreferenceKeys.ACCESS_TOKEN, null) } returns null

        // When
        val result = preferenceUseCase.getAccessToken()

        // Then
        assertNull(result)
    }

    @Test
    fun `setAccessToken saves value correctly`() {
        // Given
        val token = "new_access_token"
        every { mockEditor.putString(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.setAccessToken(token)

        // Then
        verify { mockEditor.putString(PreferenceKeys.ACCESS_TOKEN, token) }
        verify { mockEditor.apply() }
    }

    // ========== Tests para RefreshToken ==========

    @Test
    fun `getRefreshToken returns saved value`() {
        // Given
        val expectedToken = "test_refresh_token"
        every { mockSharedPreferences.getString(PreferenceKeys.REFRESH_TOKEN, null) } returns expectedToken

        // When
        val result = preferenceUseCase.getRefreshToken()

        // Then
        assertEquals(expectedToken, result)
    }

    @Test
    fun `setRefreshToken saves value correctly`() {
        // Given
        val token = "new_refresh_token"
        every { mockEditor.putString(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.setRefreshToken(token)

        // Then
        verify { mockEditor.putString(PreferenceKeys.REFRESH_TOKEN, token) }
        verify { mockEditor.apply() }
    }

    // ========== Tests para IdUser ==========

    @Test
    fun `getIdUser returns saved value`() {
        // Given
        val expectedId = 123
        every { mockSharedPreferences.contains(PreferenceKeys.ID_USER) } returns true
        every { mockSharedPreferences.getInt(PreferenceKeys.ID_USER, -1) } returns expectedId

        // When
        val result = preferenceUseCase.getIdUser()

        // Then
        assertEquals(expectedId, result)
    }

    @Test
    fun `getIdUser returns null when not set`() {
        // Given
        every { mockSharedPreferences.contains(PreferenceKeys.ID_USER) } returns false

        // When
        val result = preferenceUseCase.getIdUser()

        // Then
        assertNull(result)
    }

    @Test
    fun `setIdUser saves value correctly`() {
        // Given
        val userId = 456
        every { mockEditor.putInt(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.setIdUser(userId)

        // Then
        verify { mockEditor.putInt(PreferenceKeys.ID_USER, userId) }
        verify { mockEditor.apply() }
    }

    // ========== Tests para IdCycleSchool ==========

    @Test
    fun `getIdCycleSchool returns saved value`() {
        // Given
        val expectedId = 789
        every { mockSharedPreferences.contains(PreferenceKeys.ID_CYCLE_SCHOOL) } returns true
        every { mockSharedPreferences.getInt(PreferenceKeys.ID_CYCLE_SCHOOL, -1) } returns expectedId

        // When
        val result = preferenceUseCase.getIdCycleSchool()

        // Then
        assertEquals(expectedId, result)
    }

    @Test
    fun `setIdCycleSchool saves value correctly`() {
        // Given
        val cycleId = 101
        every { mockEditor.putInt(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.setIdCycleSchool(cycleId)

        // Then
        verify { mockEditor.putInt(PreferenceKeys.ID_CYCLE_SCHOOL, cycleId) }
        verify { mockEditor.apply() }
    }

    // ========== Tests para IdFormativeField ==========

    @Test
    fun `getIdFormativeField returns saved value`() {
        // Given
        val expectedId = 202
        every { mockSharedPreferences.contains(PreferenceKeys.ID_FORMATIVE_FIELD) } returns true
        every { mockSharedPreferences.getInt(PreferenceKeys.ID_FORMATIVE_FIELD, -1) } returns expectedId

        // When
        val result = preferenceUseCase.getIdFormativeField()

        // Then
        assertEquals(expectedId, result)
    }

    @Test
    fun `setIdFormativeField saves value correctly`() {
        // Given
        val fieldId = 303
        every { mockEditor.putInt(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.setIdFormativeField(fieldId)

        // Then
        verify { mockEditor.putInt(PreferenceKeys.ID_FORMATIVE_FIELD, fieldId) }
        verify { mockEditor.apply() }
    }

    // ========== Tests para IdPartial ==========

    @Test
    fun `getIdPartial returns saved value`() {
        // Given
        val expectedId = 404
        every { mockSharedPreferences.contains(PreferenceKeys.ID_PARTIAL) } returns true
        every { mockSharedPreferences.getInt(PreferenceKeys.ID_PARTIAL, -1) } returns expectedId

        // When
        val result = preferenceUseCase.getIdPartial()

        // Then
        assertEquals(expectedId, result)
    }

    @Test
    fun `setIdPartial saves value correctly`() {
        // Given
        val partialId = 505
        every { mockEditor.putInt(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.setIdPartial(partialId)

        // Then
        verify { mockEditor.putInt(PreferenceKeys.ID_PARTIAL, partialId) }
        verify { mockEditor.apply() }
    }

    // ========== Tests para IdUserLevel ==========

    @Test
    fun `getIdUserLevel returns saved value`() {
        // Given
        val expectedId = 606
        every { mockSharedPreferences.contains(PreferenceKeys.ID_USER_LEVEL) } returns true
        every { mockSharedPreferences.getInt(PreferenceKeys.ID_USER_LEVEL, -1) } returns expectedId

        // When
        val result = preferenceUseCase.getIdUserLevel()

        // Then
        assertEquals(expectedId, result)
    }

    @Test
    fun `setIdUserLevel saves value correctly`() {
        // Given
        val levelId = 707
        every { mockEditor.putInt(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.setIdUserLevel(levelId)

        // Then
        verify { mockEditor.putInt(PreferenceKeys.ID_USER_LEVEL, levelId) }
        verify { mockEditor.apply() }
    }

    // ========== Tests para RangeDatesPartial ==========

    @Test
    fun `getRangeDatesPartial returns saved value`() {
        // Given
        val expectedRange = "2024-01-01 / 2024-03-31"
        every { mockSharedPreferences.getString(PreferenceKeys.RANGE_DATES_PARTIAL, null) } returns expectedRange

        // When
        val result = preferenceUseCase.getRangeDatesPartial()

        // Then
        assertEquals(expectedRange, result)
    }

    @Test
    fun `setRangeDatesPartial saves value correctly`() {
        // Given
        val range = "2024-01-01 / 2024-03-31"
        every { mockEditor.putString(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.setRangeDatesPartial(range)

        // Then
        verify { mockEditor.putString(PreferenceKeys.RANGE_DATES_PARTIAL, range) }
        verify { mockEditor.apply() }
    }

    @Test
    fun `setRangeDatesPartial with null removes value`() {
        // Given
        every { mockEditor.remove(any()) } returns mockEditor

        // When
        preferenceUseCase.setRangeDatesPartial(null)

        // Then
        verify { mockEditor.remove(PreferenceKeys.RANGE_DATES_PARTIAL) }
        verify { mockEditor.apply() }
    }

    // ========== Tests para RememberLogin ==========

    @Test
    fun `getRememberLogin returns saved value`() {
        // Given
        every { mockSharedPreferences.getBoolean(PreferenceKeys.REMEMBER_LOGIN, false) } returns true

        // When
        val result = preferenceUseCase.getRememberLogin()

        // Then
        assertTrue(result)
    }

    @Test
    fun `getRememberLogin returns false when not set`() {
        // Given
        every { mockSharedPreferences.getBoolean(PreferenceKeys.REMEMBER_LOGIN, false) } returns false

        // When
        val result = preferenceUseCase.getRememberLogin()

        // Then
        assertFalse(result)
    }

    @Test
    fun `setRememberLogin saves value correctly`() {
        // Given
        every { mockEditor.putBoolean(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.setRememberLogin(true)

        // Then
        verify { mockEditor.putBoolean(PreferenceKeys.REMEMBER_LOGIN, true) }
        verify { mockEditor.apply() }
    }

    // ========== Tests para cleanPreference ==========

    @Test
    fun `cleanPreference calls repository cleanPreference`() {
        // Given
        every { mockRepository.cleanPreference() } returns true

        // When
        val result = preferenceUseCase.cleanPreference()

        // Then
        verify { mockRepository.cleanPreference() }
        assertTrue(result)
    }

    // ========== Tests genéricos para get y set ==========

    @Test
    fun `get with Preference returns correct value`() {
        // Given
        val expectedValue = "test_value"
        every { mockSharedPreferences.getString(PreferenceKeys.ACCESS_TOKEN, null) } returns expectedValue

        // When
        val result = preferenceUseCase.get(Preference.AccessToken)

        // Then
        assertEquals(expectedValue, result)
    }

    @Test
    fun `set with Preference saves value correctly`() {
        // Given
        val value = "test_value"
        every { mockEditor.putString(any(), any()) } returns mockEditor

        // When
        preferenceUseCase.set(Preference.AccessToken, value)

        // Then
        verify { mockEditor.putString(PreferenceKeys.ACCESS_TOKEN, value) }
        verify { mockEditor.apply() }
    }
}
