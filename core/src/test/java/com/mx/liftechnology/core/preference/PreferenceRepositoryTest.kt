package com.mx.liftechnology.core.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [PreferenceRepositoryImpl].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class PreferenceRepositoryTest {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var masterKey: MasterKey
    private lateinit var repository: PreferenceRepositoryImpl

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        sharedPreferences = mockk(relaxed = true)
        editor = mockk(relaxed = true)
        masterKey = mockk(relaxed = true)

        mockkStatic(EncryptedSharedPreferences::class)
        mockkStatic(MasterKey.Builder::class)

        every { MasterKey.Builder(context) } returns mockk {
            every { setKeyScheme(any()) } returns this
            every { build() } returns masterKey
        }

        every {
            EncryptedSharedPreferences.create(
                context,
                any(),
                masterKey,
                any(),
                any()
            )
        } returns sharedPreferences

        every { sharedPreferences.edit() } returns editor
        every { editor.apply() } returns Unit
        every { editor.clear() } returns editor
        every { editor.putString(any(), any()) } returns editor
        every { editor.putInt(any(), any()) } returns editor
        every { editor.putBoolean(any(), any()) } returns editor
        every { editor.putFloat(any(), any()) } returns editor
        every { editor.putLong(any(), any()) } returns editor

        repository = PreferenceRepositoryImpl(context)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getPreference devuelve String guardado`() {
        val key = "test_key"
        val value = "test_value"
        every { sharedPreferences.getString(key, null) } returns value

        val result = repository.getPreference(key, null as String?)

        assertEquals(value, result)
    }

    @Test
    fun `getPreference devuelve Int guardado`() {
        val key = "test_key"
        val value = 42
        every { sharedPreferences.getInt(key, 0) } returns value

        val result = repository.getPreference(key, 0)

        assertEquals(value, result)
    }

    @Test
    fun `getPreference devuelve Boolean guardado`() {
        val key = "test_key"
        val value = true
        every { sharedPreferences.getBoolean(key, false) } returns value

        val result = repository.getPreference(key, false)

        assertEquals(value, result)
    }

    @Test
    fun `savePreference guarda String correctamente`() {
        val key = "test_key"
        val value = "test_value"

        repository.savePreference(key, value)

        verify { editor.putString(key, value) }
        verify { editor.apply() }
    }

    @Test
    fun `savePreference guarda Int correctamente`() {
        val key = "test_key"
        val value = 42

        repository.savePreference(key, value)

        verify { editor.putInt(key, value) }
        verify { editor.apply() }
    }

    @Test
    fun `savePreference guarda Boolean correctamente`() {
        val key = "test_key"
        val value = true

        repository.savePreference(key, value)

        verify { editor.putBoolean(key, value) }
        verify { editor.apply() }
    }

    @Test
    fun `cleanPreference limpia todas las preferencias`() {
        every { sharedPreferences.edit() } returns editor

        val result = repository.cleanPreference()

        assertTrue(result)
        verify { editor.clear() }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `getPreference lanza excepción para tipo no soportado`() {
        repository.getPreference("test_key", 1.0) // Double no soportado
    }

    @Test(expected = IllegalArgumentException::class)
    fun `savePreference lanza excepción para tipo no soportado`() {
        repository.savePreference("test_key", 1.0) // Double no soportado
    }
}

