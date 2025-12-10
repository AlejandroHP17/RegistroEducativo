package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseEditStudent
import com.mx.liftechnology.core.network.api.ResponseGetStudent
import com.mx.liftechnology.core.network.api.ResponseRegisterStudent
import com.mx.liftechnology.data.mapper.StudentMapper.toEditStudentDomain
import com.mx.liftechnology.data.mapper.StudentMapper.toListStudentDomain
import com.mx.liftechnology.data.mapper.StudentMapper.toStudentDomain
import com.mx.liftechnology.data.mapper.StudentMapper.toStringDomain
import com.mx.liftechnology.domain.model.student.StudentDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests para [StudentMapper].
 * 
 * Verifica que todos los mappers de estudiantes funcionen correctamente con diferentes escenarios:
 * - Listas con múltiples elementos
 * - Listas vacías
 * - Datos completos
 * - Datos con valores nulos
 * - Datos con valores vacíos
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class StudentMapperTest {

    // ========== Tests para toListStudentDomain ==========

    @Test
    fun `toListStudentDomain con lista completa mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetStudent(
                studentId = 1,
                curp = "CURP001",
                name = "Juan",
                lastName = "Pérez",
                secondLastName = "García",
                birthday = "2000-01-01",
                phoneNumber = "1234567890",
                userId = 10
            ),
            ResponseGetStudent(
                studentId = 2,
                curp = "CURP002",
                name = "María",
                lastName = "López",
                secondLastName = "Martínez",
                birthday = "2001-02-02",
                phoneNumber = "0987654321",
                userId = 11
            )
        )

        // When
        val result = responseList.toListStudentDomain()

        // Then
        assertEquals(2, result.size)
        assertEquals(1, result[0].studentId)
        assertEquals("CURP001", result[0].curp)
        assertEquals("Juan", result[0].name)
        assertEquals("Pérez", result[0].lastName)
        assertEquals("García", result[0].secondLastName)
        assertEquals("2000-01-01", result[0].birthday)
        assertEquals("1234567890", result[0].phoneNumber)
        assertEquals(10, result[0].userId)

        assertEquals(2, result[1].studentId)
        assertEquals("CURP002", result[1].curp)
        assertEquals("María", result[1].name)
    }

    @Test
    fun `toListStudentDomain con lista vacía retorna lista vacía`() {
        // Given
        val responseList = emptyList<ResponseGetStudent>()

        // When
        val result = responseList.toListStudentDomain()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `toListStudentDomain con valores nulos mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetStudent(
                studentId = null,
                curp = null,
                name = null,
                lastName = null,
                secondLastName = null,
                birthday = null,
                phoneNumber = null,
                userId = null
            )
        )

        // When
        val result = responseList.toListStudentDomain()

        // Then
        assertEquals(1, result.size)
        assertNull(result[0].studentId)
        assertNull(result[0].curp)
        assertNull(result[0].name)
        assertNull(result[0].lastName)
        assertNull(result[0].secondLastName)
        assertNull(result[0].birthday)
        assertNull(result[0].phoneNumber)
        assertNull(result[0].userId)
    }

    @Test
    fun `toListStudentDomain con valores vacíos mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetStudent(
                studentId = 0,
                curp = "",
                name = "",
                lastName = "",
                secondLastName = "",
                birthday = "",
                phoneNumber = "",
                userId = 0
            )
        )

        // When
        val result = responseList.toListStudentDomain()

        // Then
        assertEquals(1, result.size)
        assertEquals(0, result[0].studentId)
        assertEquals("", result[0].curp)
        assertEquals("", result[0].name)
        assertEquals("", result[0].lastName)
        assertEquals("", result[0].secondLastName)
        assertEquals("", result[0].birthday)
        assertEquals("", result[0].phoneNumber)
        assertEquals(0, result[0].userId)
    }

    // ========== Tests para toStudentDomain ==========

    @Test
    fun `toStudentDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseRegisterStudent(
            studentId = 1,
            curp = "CURP001",
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            birthday = "2000-01-01",
            phoneNumber = "1234567890"
        )

        // When
        val result = response.toStudentDomain()

        // Then
        assertEquals(1, result.studentId)
        assertEquals("CURP001", result.curp)
        assertEquals("Juan", result.name)
        assertEquals("Pérez", result.lastName)
        assertEquals("García", result.secondLastName)
        assertEquals("2000-01-01", result.birthday)
        assertEquals("1234567890", result.phoneNumber)
        assertEquals(1, result.userId) // userId debe ser igual a studentId
    }

    @Test
    fun `toStudentDomain con valores nulos mapea correctamente`() {
        // Given
        val response = ResponseRegisterStudent(
            studentId = null,
            curp = null,
            name = null,
            lastName = null,
            secondLastName = null,
            birthday = null,
            phoneNumber = null
        )

        // When
        val result = response.toStudentDomain()

        // Then
        assertNull(result.studentId)
        assertNull(result.curp)
        assertNull(result.name)
        assertNull(result.lastName)
        assertNull(result.secondLastName)
        assertNull(result.birthday)
        assertNull(result.phoneNumber)
        assertNull(result.userId)
    }

    @Test
    fun `toStudentDomain mapea userId desde studentId`() {
        // Given
        val response = ResponseRegisterStudent(
            studentId = 999,
            curp = "CURP999",
            name = "Test",
            lastName = "Test",
            secondLastName = "Test",
            birthday = "2000-01-01",
            phoneNumber = "1234567890"
        )

        // When
        val result = response.toStudentDomain()

        // Then
        assertEquals(999, result.studentId)
        assertEquals(999, result.userId)
        assertEquals(response.studentId, result.userId)
    }

    // ========== Tests para toEditStudentDomain ==========

    @Test
    fun `toEditStudentDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseEditStudent(
            studentId = 1,
            curp = "CURP001",
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            birthday = "2000-01-01",
            phoneNumber = "1234567890"
        )

        // When
        val result = response.toEditStudentDomain()

        // Then
        assertEquals(1, result.studentId)
        assertEquals("CURP001", result.curp)
        assertEquals("Juan", result.name)
        assertEquals("Pérez", result.lastName)
        assertEquals("García", result.secondLastName)
        assertEquals("2000-01-01", result.birthday)
        assertEquals("1234567890", result.phoneNumber)
        assertEquals(1, result.userId) // userId debe ser igual a studentId
    }

    @Test
    fun `toEditStudentDomain con valores nulos mapea correctamente`() {
        // Given
        val response = ResponseEditStudent(
            studentId = null,
            curp = null,
            name = null,
            lastName = null,
            secondLastName = null,
            birthday = null,
            phoneNumber = null
        )

        // When
        val result = response.toEditStudentDomain()

        // Then
        assertNull(result.studentId)
        assertNull(result.curp)
        assertNull(result.name)
        assertNull(result.lastName)
        assertNull(result.secondLastName)
        assertNull(result.birthday)
        assertNull(result.phoneNumber)
        assertNull(result.userId)
    }

    @Test
    fun `toEditStudentDomain mapea userId desde studentId`() {
        // Given
        val response = ResponseEditStudent(
            studentId = 888,
            curp = "CURP888",
            name = "Edit",
            lastName = "Test",
            secondLastName = "Test",
            birthday = "2000-01-01",
            phoneNumber = "1234567890"
        )

        // When
        val result = response.toEditStudentDomain()

        // Then
        assertEquals(888, result.studentId)
        assertEquals(888, result.userId)
        assertEquals(response.studentId, result.userId)
    }

    // ========== Tests para toStringDomain ==========

    @Test
    fun `toStringDomain con string normal retorna el mismo string`() {
        // Given
        val input = "Test String"

        // When
        val result = input.toStringDomain()

        // Then
        assertEquals("Test String", result)
    }

    @Test
    fun `toStringDomain con string vacío retorna string vacío`() {
        // Given
        val input = ""

        // When
        val result = input.toStringDomain()

        // Then
        assertEquals("", result)
    }

    @Test
    fun `toStringDomain con string con espacios retorna el mismo string`() {
        // Given
        val input = "  Test  String  "

        // When
        val result = input.toStringDomain()

        // Then
        assertEquals("  Test  String  ", result)
    }

    @Test
    fun `toStringDomain con string especiales retorna el mismo string`() {
        // Given
        val input = "Test@123#Special$Chars"

        // When
        val result = input.toStringDomain()

        // Then
        assertEquals("Test@123#Special$Chars", result)
    }
}
