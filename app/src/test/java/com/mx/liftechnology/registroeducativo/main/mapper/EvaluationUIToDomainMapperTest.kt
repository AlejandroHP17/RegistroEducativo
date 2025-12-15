package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCardStudent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests para [EvaluationUIToDomainMapper].
 */
class EvaluationUIToDomainMapperTest {

    @Test
    fun `toModelCard mapea correctamente estudiantes validos`() {
        // Given
        val students = listOf(
            CustomCardStudent(
                id = "1",
                numberList = "1",
                studentName = "Juan Perez",
                score = ModelStateOutFieldText(valueText = "9.5")
            ),
            CustomCardStudent(
                id = "2",
                numberList = "2",
                studentName = "Ana Lopez",
                score = ModelStateOutFieldText(valueText = "10.0")
            )
        )

        // When
        val result = EvaluationUIToDomainMapper.run { students.toModelCard() }

        // Then
        assertEquals(2, result.size)
        assertEquals(1, result[0].studentId)
        assertEquals(9.5, result[0].grade!!, 0.001)
        assertEquals(2, result[1].studentId)
        assertEquals(10.0, result[1].grade!!, 0.001)
    }

    @Test
    fun `toModelCard ignora estudiantes con id invalido`() {
        // Given
        val students = listOf(
            CustomCardStudent(
                id = "0", // inválido (<= 0)
                numberList = "1",
                studentName = "Invalido",
                score = ModelStateOutFieldText(valueText = "8.0")
            ),
            CustomCardStudent(
                id = "abc", // no numérico
                numberList = "2",
                studentName = "Texto",
                score = ModelStateOutFieldText(valueText = "7.0")
            ),
            CustomCardStudent(
                id = "3",
                numberList = "3",
                studentName = "Valido",
                score = ModelStateOutFieldText(valueText = "") // sin calificación
            )
        )

        // When
        val result = EvaluationUIToDomainMapper.run { students.toModelCard() }

        // Then
        assertEquals(1, result.size)
        assertEquals(3, result[0].studentId)
        // grade puede ser null si no hay calificación
        assertTrue(result[0].grade == null)
    }
}
