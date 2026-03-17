package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseGetByFieldTypeStudent
import com.mx.liftechnology.core.network.api.ResponseGetListByFieldStudent
import com.mx.liftechnology.core.network.api.ResponseGetListByFieldTypeStudent
import com.mx.liftechnology.core.network.api.ResponseGetListEvaluationsStudent
import com.mx.liftechnology.core.network.api.ResponseGetListWorkStudents
import com.mx.liftechnology.core.network.api.ResponseListWork
import com.mx.liftechnology.core.network.api.ResponseListWorkStudent
import com.mx.liftechnology.core.network.api.ResponseWorkTypeEvaluations
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toByFieldTypeStudentDomain
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toEvaluationsStudentDomain
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toWorkTypeEvaluationDomain
import com.mx.liftechnology.data.mapper.EvaluationsMapper.toWorkTypeFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeEvaluationDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.ByFieldTypeStudentDomain
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests para [EvaluationsMapper].
 * 
 * Verifica que todos los mappers de evaluaciones funcionen correctamente con diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class EvaluationsMapperTest {

    // ========== Tests para toWorkTypeEvaluationDomain ==========

    @Test
    fun `toWorkTypeEvaluationDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseWorkTypeEvaluations(
            createdWorks = 5,
            totalStudentsWithGrade = 10,
            totalStudentsWithoutGrade = 2,
            formativeFieldName = "Matemáticas",
            partialName = "Parcial 1",
            workTypeId = 1,
            workTypeName = "Examen",
            nameWork = "Examen Final"
        )

        // When
        val result = response.toWorkTypeEvaluationDomain()

        // Then
        assertEquals(5, result.createdWorks)
        assertEquals(10, result.totalStudentsWithGrade)
        assertEquals(2, result.totalStudentsWithoutGrade)
        assertEquals("Matemáticas", result.formativeFieldName)
        assertEquals("Parcial 1", result.partialName)
        assertEquals(1, result.workTypeId)
        assertEquals("Examen", result.workTypeName)
        assertEquals("Examen Final", result.nameWork)
    }

    @Test
    fun `toWorkTypeEvaluationDomain con valores cero mapea correctamente`() {
        // Given
        val response = ResponseWorkTypeEvaluations(
            createdWorks = 0,
            totalStudentsWithGrade = 0,
            totalStudentsWithoutGrade = 0,
            formativeFieldName = "",
            partialName = "",
            workTypeId = 0,
            workTypeName = "",
            nameWork = ""
        )

        // When
        val result = response.toWorkTypeEvaluationDomain()

        // Then
        assertEquals(0, result.createdWorks)
        assertEquals(0, result.totalStudentsWithGrade)
        assertEquals(0, result.totalStudentsWithoutGrade)
        assertEquals("", result.formativeFieldName)
        assertEquals("", result.partialName)
        assertEquals(0, result.workTypeId)
        assertEquals("", result.workTypeName)
        assertEquals("", result.nameWork)
    }

    // ========== Tests para toWorkTypeFormativeFieldDomain ==========

    @Test
    fun `toWorkTypeFormativeFieldDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseGetListWorkStudents(
            formativeFieldId = 1,
            nameFormativeField = "Matemáticas",
            listWorks = listOf(
                ResponseListWork(
                    workId = 1,
                    workName = "Examen 1",
                    listWorks = listOf(
                        ResponseListWorkStudent(
                            workStudentId = 1,
                            workStudentName = "Trabajo 1",
                            workStudentDate = "2024-01-01"
                        )
                    )
                )
            )
        )

        // When
        val result = response.toWorkTypeFormativeFieldDomain()

        // Then
        assertEquals(1, result.formativeFieldId)
        assertEquals("Matemáticas", result.nameFormativeField)
        assertEquals(1, result.listWorks.size)
        assertEquals(1, result.listWorks[0].workId)
        assertEquals("Examen 1", result.listWorks[0].workName)
    }

    @Test
    fun `toWorkTypeFormativeFieldDomain con lista vacía mapea correctamente`() {
        // Given
        val response = ResponseGetListWorkStudents(
            formativeFieldId = 1,
            nameFormativeField = "Matemáticas",
            listWorks = emptyList()
        )

        // When
        val result = response.toWorkTypeFormativeFieldDomain()

        // Then
        assertEquals(1, result.formativeFieldId)
        assertEquals("Matemáticas", result.nameFormativeField)
        assertTrue(result.listWorks.isEmpty())
    }

    @Test
    fun `toWorkTypeFormativeFieldDomain con elementos null los omite`() {
        // Given
        val response = ResponseGetListWorkStudents(
            formativeFieldId = 1,
            nameFormativeField = "Matemáticas",
            listWorks = listOf(
                ResponseListWork(
                    workId = 1,
                    workName = "Examen 1",
                    listWorks = listOf(null, null)
                )
            )
        )

        // When
        val result = response.toWorkTypeFormativeFieldDomain()

        // Then
        assertEquals(1, result.listWorks.size)
        assertTrue(result.listWorks[0].listWorks.isEmpty())
    }

    // ========== Tests para toEvaluationsStudentDomain ==========

    @Test
    fun `toEvaluationsStudentDomain con lista completa mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetListEvaluationsStudent(
                studentId = 1,
                evaluationName = "Examen 1",
                grade = 8.5,
                workDate = "2024-01-01",
                evaluationId = 10
            ),
            ResponseGetListEvaluationsStudent(
                studentId = 2,
                evaluationName = "Examen 2",
                grade = 9.0,
                workDate = "2024-01-02",
                evaluationId = 11
            )
        )

        // When
        val result = responseList.toEvaluationsStudentDomain()

        // Then
        assertEquals(2, result.size)
        assertEquals(1, result[0].studentId)
        assertEquals("Examen 1", result[0].evaluationName)
        assertEquals(8.5, result[0].grade, 0.01)
        assertEquals("2024-01-01", result[0].workDate)
        assertEquals(10, result[0].evaluationId)

        assertEquals(2, result[1].studentId)
        assertEquals("Examen 2", result[1].evaluationName)
        assertEquals(9.0, result[1].grade, 0.01)
    }

    @Test
    fun `toEvaluationsStudentDomain con lista vacía retorna lista vacía`() {
        // Given
        val responseList = emptyList<ResponseGetListEvaluationsStudent>()

        // When
        val result = responseList.toEvaluationsStudentDomain()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `toEvaluationsStudentDomain con valores nulos mapea correctamente`() {
        // Given
        val responseList = listOf(
            ResponseGetListEvaluationsStudent(
                studentId = null,
                evaluationName = null,
                grade = null,
                workDate = null,
                evaluationId = null
            )
        )

        // When
        val result = responseList.toEvaluationsStudentDomain()

        // Then
        assertEquals(1, result.size)
        assertNull(result[0].studentId)
        assertNull(result[0].evaluationName)
        assertNull(result[0].grade)
        assertNull(result[0].workDate)
        assertNull(result[0].evaluationId)
    }

    // ========== Tests para toByFieldTypeStudentDomain ==========

    @Test
    fun `toByFieldTypeStudentDomain con datos completos mapea correctamente`() {
        // Given
        val response = ResponseGetByFieldTypeStudent(
            formativeFieldId = 1,
            formativeFieldName = "Matemáticas",
            workTypeId = 1,
            workTypeName = "Examen",
            works = listOf(
                ResponseGetListByFieldTypeStudent(
                    workId = 1,
                    workName = "Examen 1",
                    workDate = "2024-01-01",
                    listStudents = listOf(
                        ResponseGetListByFieldStudent(
                            studentId = 1,
                            studentName = "Juan Pérez",
                            grade = 8.5
                        )
                    )
                )
            )
        )

        // When
        val result = response.toByFieldTypeStudentDomain()

        // Then
        assertEquals(1, result.formativeFieldId)
        assertEquals("Matemáticas", result.formativeFieldName)
        assertEquals(1, result.workTypeId)
        assertEquals("Examen", result.workTypeName)
        assertEquals(1, result.works.size)
        assertEquals(1, result.works[0].workId)
        assertEquals("Examen 1", result.works[0].workName)
        assertEquals(1, result.works[0].listStudents.size)
        assertEquals(1, result.works[0].listStudents[0].studentId)
        assertEquals("Juan Pérez", result.works[0].listStudents[0].studentName)
        assertEquals(8.5, result.works[0].listStudents[0].grade, 0.01)
    }

    @Test
    fun `toByFieldTypeStudentDomain con lista vacía mapea correctamente`() {
        // Given
        val response = ResponseGetByFieldTypeStudent(
            formativeFieldId = 1,
            formativeFieldName = "Matemáticas",
            workTypeId = 1,
            workTypeName = "Examen",
            works = emptyList()
        )

        // When
        val result = response.toByFieldTypeStudentDomain()

        // Then
        assertEquals(1, result.formativeFieldId)
        assertTrue(result.works.isEmpty())
    }

    @Test
    fun `toByFieldTypeStudentDomain con elementos null los omite`() {
        // Given
        val response = ResponseGetByFieldTypeStudent(
            formativeFieldId = 1,
            formativeFieldName = "Matemáticas",
            workTypeId = 1,
            workTypeName = "Examen",
            works = listOf(null, null)
        )

        // When
        val result = response.toByFieldTypeStudentDomain()

        // Then
        assertTrue(result.works.isEmpty())
    }
}
