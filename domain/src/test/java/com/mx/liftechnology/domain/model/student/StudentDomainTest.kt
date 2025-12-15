package com.mx.liftechnology.domain.model.student

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests simples para [StudentDomain].
 *
 * El objetivo es asegurar que el modelo de dominio expone correctamente sus propiedades
 * y se comporta como un data class inmutable.
 */
class StudentDomainTest {

    @Test
    fun `StudentDomain almacena correctamente los valores proporcionados`() {
        // Given
        val student = StudentDomain(
            studentId = 1,
            curp = "CURP123456HDFLRS09",
            birthday = "2000-01-01",
            phoneNumber = "5512345678",
            userId = 10,
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "López"
        )

        // Then
        assertEquals(1, student.studentId)
        assertEquals("CURP123456HDFLRS09", student.curp)
        assertEquals("2000-01-01", student.birthday)
        assertEquals("5512345678", student.phoneNumber)
        assertEquals(10, student.userId)
        assertEquals("Juan", student.name)
        assertEquals("Pérez", student.lastName)
        assertEquals("López", student.secondLastName)
    }

    @Test
    fun `copy de StudentDomain crea una nueva instancia con cambios`() {
        // Given
        val original = StudentDomain(
            studentId = 1,
            curp = "CURP123456HDFLRS09",
            birthday = "2000-01-01",
            phoneNumber = "5512345678",
            userId = 10,
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "López"
        )

        // When
        val copy = original.copy(name = "Pedro", phoneNumber = "5599999999")

        // Then
        assertEquals(1, copy.studentId)
        assertEquals("CURP123456HDFLRS09", copy.curp)
        assertEquals("2000-01-01", copy.birthday)
        assertEquals("5599999999", copy.phoneNumber)
        assertEquals(10, copy.userId)
        assertEquals("Pedro", copy.name)
        assertEquals("Pérez", copy.lastName)
        assertEquals("López", copy.secondLastName)

        // El original no debe cambiar
        assertEquals("Juan", original.name)
        assertEquals("5512345678", original.phoneNumber)
    }
}
