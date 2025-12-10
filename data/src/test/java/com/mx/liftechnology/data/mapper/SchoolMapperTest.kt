package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseCctSchool
import com.mx.liftechnology.core.network.api.ResponsePeriodCatalog
import com.mx.liftechnology.data.mapper.SchoolMapper.toCCTDomain
import com.mx.liftechnology.domain.model.schoolCycle.CCTDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests para [SchoolMapper].
 * 
 * Verifica que todos los mappers de escuelas funcionen correctamente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SchoolMapperTest {

    // ========== Tests para toCCTDomain ==========

    @Test
    fun `toCCTDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseCctSchool(
            schoolId = 1,
            cct = "CCT123456",
            schoolTypeId = 1,
            schoolName = "Escuela Primaria",
            shiftName = "Matutino",
            periodCatalog = listOf(
                ResponsePeriodCatalog(
                    id = 1,
                    typeName = "Bimestral",
                    periodNumber = 1
                ),
                ResponsePeriodCatalog(
                    id = 2,
                    typeName = "Bimestral",
                    periodNumber = 2
                )
            )
        )

        // When
        val result = response.toCCTDomain()

        // Then
        assertEquals(1, result.id)
        assertEquals("CCT123456", result.cct)
        assertEquals(1, result.schoolTypeId)
        assertEquals("Escuela Primaria", result.schoolName)
        assertEquals("Matutino", result.shiftName)
        assertEquals(2, result.periodCatalog.size)
        assertEquals(1, result.periodCatalog[0].id)
        assertEquals("Bimestral", result.periodCatalog[0].typeName)
        assertEquals(1, result.periodCatalog[0].periodNumber)
    }

    @Test
    fun `toCCTDomain con periodCatalog null retorna lista vacía`() {
        // Given
        val response = ResponseCctSchool(
            schoolId = 1,
            cct = "CCT123456",
            schoolTypeId = 1,
            schoolName = "Escuela Primaria",
            shiftName = "Matutino",
            periodCatalog = null
        )

        // When
        val result = response.toCCTDomain()

        // Then
        assertEquals(1, result.id)
        assertTrue(result.periodCatalog.isEmpty())
    }

    @Test
    fun `toCCTDomain con periodCatalog vacío retorna lista vacía`() {
        // Given
        val response = ResponseCctSchool(
            schoolId = 1,
            cct = "CCT123456",
            schoolTypeId = 1,
            schoolName = "Escuela Primaria",
            shiftName = "Matutino",
            periodCatalog = emptyList()
        )

        // When
        val result = response.toCCTDomain()

        // Then
        assertEquals(1, result.id)
        assertTrue(result.periodCatalog.isEmpty())
    }

    @Test
    fun `toCCTDomain con elementos null en periodCatalog los omite`() {
        // Given
        val response = ResponseCctSchool(
            schoolId = 1,
            cct = "CCT123456",
            schoolTypeId = 1,
            schoolName = "Escuela Primaria",
            shiftName = "Matutino",
            periodCatalog = listOf(null, null)
        )

        // When
        val result = response.toCCTDomain()

        // Then
        assertEquals(1, result.id)
        assertTrue(result.periodCatalog.isEmpty())
    }

    @Test
    fun `toCCTDomain con valores nulos mapea correctamente`() {
        // Given
        val response = ResponseCctSchool(
            schoolId = null,
            cct = null,
            schoolTypeId = null,
            schoolName = null,
            shiftName = null,
            periodCatalog = null
        )

        // When
        val result = response.toCCTDomain()

        // Then
        assertNull(result.id)
        assertNull(result.cct)
        assertNull(result.schoolTypeId)
        assertNull(result.schoolName)
        assertNull(result.shiftName)
        assertTrue(result.periodCatalog.isEmpty())
    }

    @Test
    fun `toCCTDomain mapea schoolId a id`() {
        // Given
        val response = ResponseCctSchool(
            schoolId = 999,
            cct = "CCT999",
            schoolTypeId = 1,
            schoolName = "Test",
            shiftName = "Test",
            periodCatalog = null
        )

        // When
        val result = response.toCCTDomain()

        // Then
        assertEquals(999, result.id)
        assertEquals(response.schoolId, result.id)
    }
}
