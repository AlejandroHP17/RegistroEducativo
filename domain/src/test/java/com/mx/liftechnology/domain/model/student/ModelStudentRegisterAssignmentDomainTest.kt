package com.mx.liftechnology.domain.model.student

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetStudent
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests para la función de extensión [toModelStudentRegisterAssignmentList].
 * Verifica que la conversión del modelo de red al de dominio, incluyendo ordenamiento y numeración, sea correcta.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ModelStudentRegisterAssignmentDomainTest {

    /**
     * Test para verificar que una lista de [ResponseGetStudent] se convierte, ordena y numera correctamente.
     */
    @Test
    fun `toModelStudentRegisterAssignmentList con lista valida`() {
        // Preparamos los datos de entrada desordenados
        val responseList = listOf(
            ResponseGetStudent(id = "2", studentId = "102", teacherSchoolCycleGroupId = "202", curp = "...", birthday = "...", phoneNumber = "...", userId = "302", name = "Maria", lastName = "Lopez", secondLastName = "Garcia"),
            ResponseGetStudent(id = "1", studentId = "101", teacherSchoolCycleGroupId = "201", curp = "...", birthday = "...", phoneNumber = "...", userId = "301", name = "Juan", lastName = "Perez", secondLastName = "Gomez")
        )

        // Ejecutamos la función de extensión
        val result = responseList.toModelStudentRegisterAssignmentList()

        // Verificamos el resultado
        assertEquals(2, result.size)
        // Comprobamos el ordenamiento (Juan Perez debe ir antes que Maria Lopez)
        assertEquals("101", result[1].studentId)
        assertEquals("Perez Gomez Juan", result[1].completeName)
        assertEquals(1, result[0].listNumber) // Verificamos el número de lista

        assertEquals("102", result[0].studentId)
        assertEquals("Lopez Garcia Maria", result[0].completeName)
        assertEquals(2, result[1].listNumber)
    }

    /**
     * Test para verificar que una lista nula o vacía se maneja correctamente, devolviendo una lista vacía.
     */
    @Test
    fun `toModelStudentRegisterAssignmentList con lista nula o vacia`() {
        // Preparamos los datos de entrada
        val nullList: List<ResponseGetStudent>? = null
        val emptyList = emptyList<ResponseGetStudent>()

        // Ejecutamos la función de extensión
        val resultFromNull = nullList.toModelStudentRegisterAssignmentList()
        val resultFromEmpty = emptyList.toModelStudentRegisterAssignmentList()

        // Verificamos el resultado
        assertEquals(0, resultFromNull.size)
        assertEquals(0, resultFromEmpty.size)
    }

    /**
     * Test para verificar que los elementos nulos dentro de una lista son omitidos durante la conversión.
     */
    @Test
    fun `toModelStudentRegisterAssignmentList con elementos nulos en la lista`() {
        // Preparamos los datos de entrada
        val listWithNulls = listOf(
            ResponseGetStudent(id = "1", studentId = "101", teacherSchoolCycleGroupId = "201", curp = "...", birthday = "...", phoneNumber = "...", userId = "301", name = "Juan", lastName = "Perez", secondLastName = "Gomez"),
            null
        )

        // Ejecutamos la función de extensión
        val result = listWithNulls.toModelStudentRegisterAssignmentList()

        // Verificamos el resultado
        assertEquals(1, result.size)
        assertEquals("101", result[0].studentId)
    }
}