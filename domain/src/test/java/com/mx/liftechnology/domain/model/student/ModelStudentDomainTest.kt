package com.mx.liftechnology.domain.model.student

import com.mx.liftechnology.core.network.apiCall.flowMain.student.ResponseGetStudent
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests para la función de extensión [toModelStudentList].
 * Esta clase contiene los tests unitarios para verificar que la conversión del modelo de red al modelo de dominio funciona como se espera.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ModelStudentDomainTest {

    /**
     * Test para verificar que una lista de [ResponseGetStudent] se convierte correctamente a una lista de [ModelStudentDomain].
     */
    @Test
    fun `toModelStudentList con lista valida`() {
        // Preparamos los datos de entrada
        val responseList = listOf(
            ResponseGetStudent("1", "101", "201", "XAXX010101HXXIXXA0", "2001-01-01", "1234567890", "301", "Juan", "Perez", "Gomez"),
            ResponseGetStudent("2", "102", "202", "XAXX010101HXXIXXA1", "2002-02-02", "0987654321", "302", "Maria", "Lopez", "Garcia")
        )

        // Ejecutamos la función de extensión
        val result = responseList.toModelStudentList()

        // Verificamos el resultado
        assertEquals(2, result.size)
        assertEquals("101", result[0].studentId)
        assertEquals("Juan", result[0].name)
    }

    /**
     * Test para verificar que una lista nula o vacía se maneja correctamente, devolviendo una lista vacía.
     */
    @Test
    fun `toModelStudentList con lista nula o vacia`() {
        // Preparamos los datos de entrada
        val nullList: List<ResponseGetStudent>? = null
        val emptyList = emptyList<ResponseGetStudent>()

        // Ejecutamos la función de extensión
        val resultFromNull = nullList.toModelStudentList()
        val resultFromEmpty = emptyList.toModelStudentList()

        // Verificamos el resultado
        assertEquals(0, resultFromNull.size)
        assertEquals(0, resultFromEmpty.size)
    }

    /**
     * Test para verificar que los elementos nulos dentro de una lista son omitidos durante la conversión.
     */
    @Test
    fun `toModelStudentList con elementos nulos en la lista`() {
        // Preparamos los datos de entrada
        val listWithNulls = listOf(
            ResponseGetStudent("1", "101", "201", "XAXX010101HXXIXXA0", "2001-01-01", "1234567890", "301", "Juan", "Perez", "Gomez"),
            null,
            ResponseGetStudent("2", "102", "202", "XAXX010101HXXIXXA1", "2002-02-02", "0987654321", "302", "Maria", "Lopez", "Garcia")
        )

        // Ejecutamos la función de extensión
        val result = listWithNulls.toModelStudentList()

        // Verificamos el resultado
        assertEquals(2, result.size)
    }
}