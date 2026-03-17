package com.mx.liftechnology.registroeducativo.main.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Tests para [GenericMapper].
 */
class GenericMapperTest {

    @Test
    fun `toModelCustomSpinner con string numerico usa ese numero como id`() {
        // Given
        val value = "123"

        // When
        val result = GenericMapper.run { value.toModelCustomSpinner() }

        // Then
        assertNotNull(result)
        assertEquals("123", result!!.value)
        assertEquals(123, result.id)
    }

    @Test
    fun `toModelCustomSpinner con string no numerico usa hashCode como id`() {
        // Given
        val value = "ABC123"

        // When
        val result = GenericMapper.run { value.toModelCustomSpinner() }

        // Then
        assertNotNull(result)
        assertEquals("ABC123", result!!.value)
        assertEquals(value.hashCode(), result.id)
    }

    @Test
    fun `toModelCustomSpinner con string vacio retorna null`() {
        // Given
        val value = ""

        // When
        val result = GenericMapper.run { value.toModelCustomSpinner() }

        // Then
        assertNull(result)
    }
}
