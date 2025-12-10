package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseGetPartials
import com.mx.liftechnology.core.network.api.ResponseRegisterPartial
import com.mx.liftechnology.data.mapper.PartialMapper.toListPartialDomain
import com.mx.liftechnology.data.mapper.PartialMapper.toListRegisterPartialDomain
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests para [PartialMapper].
 * 
 * Verifica que todos los mappers de parciales funcionen correctamente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class PartialMapperTest {

    // ========== Tests para toListPartialDomain ==========

    @Test
    fun `toListPartialDomain con lista completa mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetPartials(
                partialId = 1,
                description = "Parcial 1",
                startDate = "2024-01-01",
                endDate = "2024-01-31"
            ),
            ResponseGetPartials(
                partialId = 2,
                description = "Parcial 2",
                startDate = "2024-02-01",
                endDate = "2024-02-28"
            )
        )

        // When
        val result = responseList.toListPartialDomain()

        // Then
        assertEquals(2, result.size)
        assertEquals(1, result[0].partialId)
        assertEquals("Parcial 1", result[0].description)
        assertEquals("2024-01-01", result[0].startDate)
        assertEquals("2024-01-31", result[0].endDate)

        assertEquals(2, result[1].partialId)
        assertEquals("Parcial 2", result[1].description)
        assertEquals("2024-02-01", result[1].startDate)
        assertEquals("2024-02-28", result[1].endDate)
    }

    @Test
    fun `toListPartialDomain con lista vacía retorna lista vacía`() {
        // Given
        val responseList = emptyList<ResponseGetPartials>()

        // When
        val result = responseList.toListPartialDomain()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `toListPartialDomain con valores nulos mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetPartials(
                partialId = 1,
                description = null,
                startDate = null,
                endDate = null
            )
        )

        // When
        val result = responseList.toListPartialDomain()

        // Then
        assertEquals(1, result.size)
        assertEquals(1, result[0].partialId)
        assertNull(result[0].description)
        assertNull(result[0].startDate)
        assertNull(result[0].endDate)
    }

    // ========== Tests para toListRegisterPartialDomain ==========

    @Test
    fun `toListRegisterPartialDomain con lista completa mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseRegisterPartial(
                partialId = 1,
                description = "Parcial 1",
                startDate = "2024-01-01",
                endDate = "2024-01-31"
            ),
            ResponseRegisterPartial(
                partialId = 2,
                description = "Parcial 2",
                startDate = "2024-02-01",
                endDate = "2024-02-28"
            )
        )

        // When
        val result = responseList.toListRegisterPartialDomain()

        // Then
        assertEquals(2, result.size)
        assertEquals(1, result[0].partialId)
        assertEquals("Parcial 1", result[0].description)
        assertEquals("2024-01-01", result[0].startDate)
        assertEquals("2024-01-31", result[0].endDate)

        assertEquals(2, result[1].partialId)
        assertEquals("Parcial 2", result[1].description)
    }

    @Test
    fun `toListRegisterPartialDomain con lista vacía retorna lista vacía`() {
        // Given
        val responseList = emptyList<ResponseRegisterPartial>()

        // When
        val result = responseList.toListRegisterPartialDomain()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `toListRegisterPartialDomain con valores nulos mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseRegisterPartial(
                partialId = 1,
                description = null,
                startDate = null,
                endDate = null
            )
        )

        // When
        val result = responseList.toListRegisterPartialDomain()

        // Then
        assertEquals(1, result.size)
        assertEquals(1, result[0].partialId)
        assertNull(result[0].description)
        assertNull(result[0].startDate)
        assertNull(result[0].endDate)
    }
}
