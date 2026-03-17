package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseGroupTeacher
import com.mx.liftechnology.core.network.api.ResponseRegisterSchoolCycle
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toRegisterSchoolCycleDomain
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toSchoolCycleDomain
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain
import com.mx.liftechnology.domain.model.schoolCycle.SchoolCycleDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests para [SchoolCycleMapper].
 * 
 * Verifica que todos los mappers de ciclos escolares funcionen correctamente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SchoolCycleMapperTest {

    // ========== Tests para toSchoolCycleDomain ==========

    @Test
    fun `toSchoolCycleDomain con lista completa mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGroupTeacher(
                teacherId = 1,
                schoolId = 10,
                name = "2024-2025",
                grade = "1°",
                groupName = "A",
                isActive = true,
                schoolCycleId = 100
            ),
            ResponseGroupTeacher(
                teacherId = 2,
                schoolId = 11,
                name = "2024-2025",
                grade = "2°",
                groupName = "B",
                isActive = false,
                schoolCycleId = 101
            )
        )

        // When
        val result = responseList.toSchoolCycleDomain()

        // Then
        assertEquals(2, result.size)
        assertEquals(1, result[0].teacherId)
        assertEquals(10, result[0].schoolId)
        assertEquals("2024-2025", result[0].name)
        assertEquals("1°", result[0].grade)
        assertEquals("A", result[0].group)
        assertEquals(true, result[0].isActive)
        assertEquals(100, result[0].cycleSchoolId)

        assertEquals(2, result[1].teacherId)
        assertEquals(11, result[1].schoolId)
        assertEquals("2°", result[1].grade)
        assertEquals("B", result[1].group)
        assertEquals(false, result[1].isActive)
    }

    @Test
    fun `toSchoolCycleDomain con lista vacía retorna lista vacía`() {
        // Given
        val responseList = emptyList<ResponseGroupTeacher>()

        // When
        val result = responseList.toSchoolCycleDomain()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `toSchoolCycleDomain con valores nulos mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGroupTeacher(
                teacherId = null,
                schoolId = null,
                name = null,
                grade = null,
                groupName = null,
                isActive = null,
                schoolCycleId = null
            )
        )

        // When
        val result = responseList.toSchoolCycleDomain()

        // Then
        assertEquals(1, result.size)
        assertNull(result[0].teacherId)
        assertNull(result[0].schoolId)
        assertNull(result[0].name)
        assertNull(result[0].grade)
        assertNull(result[0].group)
        assertNull(result[0].isActive)
        assertNull(result[0].cycleSchoolId)
    }

    @Test
    fun `toSchoolCycleDomain mapea groupName a group`() {
        // Given
        val responseList = listOf(
            ResponseGroupTeacher(
                teacherId = 1,
                schoolId = 10,
                name = "2024-2025",
                grade = "1°",
                groupName = "Grupo A",
                isActive = true,
                schoolCycleId = 100
            )
        )

        // When
        val result = responseList.toSchoolCycleDomain()

        // Then
        assertEquals("Grupo A", result[0].group)
        assertEquals(responseList[0].groupName, result[0].group)
    }

    // ========== Tests para toRegisterSchoolCycleDomain ==========

    @Test
    fun `toRegisterSchoolCycleDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseRegisterSchoolCycle(
            teacherId = 1,
            schoolId = 10,
            name = "2024-2025",
            isActive = true,
            schoolCycleId = 100
        )

        // When
        val result = response.toRegisterSchoolCycleDomain()

        // Then
        assertEquals(1, result.teacherId)
        assertEquals(10, result.schoolId)
        assertEquals("2024-2025", result.name)
        assertEquals(true, result.isActive)
        assertEquals(100, result.idCycleSchool)
    }

    @Test
    fun `toRegisterSchoolCycleDomain con valores nulos mapea correctamente`() {
        // Given
        val response = ResponseRegisterSchoolCycle(
            teacherId = null,
            schoolId = null,
            name = null,
            isActive = null,
            schoolCycleId = null
        )

        // When
        val result = response.toRegisterSchoolCycleDomain()

        // Then
        assertNull(result.teacherId)
        assertNull(result.schoolId)
        assertNull(result.name)
        assertNull(result.isActive)
        assertNull(result.idCycleSchool)
    }

    @Test
    fun `toRegisterSchoolCycleDomain mapea schoolCycleId a idCycleSchool`() {
        // Given
        val response = ResponseRegisterSchoolCycle(
            teacherId = 1,
            schoolId = 10,
            name = "2024-2025",
            isActive = true,
            schoolCycleId = 999
        )

        // When
        val result = response.toRegisterSchoolCycleDomain()

        // Then
        assertEquals(999, result.idCycleSchool)
        assertEquals(response.schoolCycleId, result.idCycleSchool)
    }
}
