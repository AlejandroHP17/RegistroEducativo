package com.mx.liftechnology.domain.usecase.share

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.repository.student.StudentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListStudentUseCase].
 */
class GetListStudentUseCaseTest {

    private lateinit var studentRepository: StudentRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: GetListStudentUseCase

    @Before
    fun setup() {
        studentRepository = mockk()
        preferenceUseCase = mockk()
        useCase = GetListStudentUseCase(studentRepository, preferenceUseCase)
    }

    @Test
    fun `retorna ErrorResult USER_INCOMPLETE_DATA cuando no hay ciclo escolar en preferencias`() = runTest {
        coEvery { preferenceUseCase.getIdCycleSchool() } returns null

        val result: ModelResult<List<StudentDomain>, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando lista devuelta esta vacia retorna ErrorResult EMPTY`() = runTest {
        coEvery { preferenceUseCase.getIdCycleSchool() } returns 1
        coEvery { studentRepository.getStudents(1) } returns SuccessResult(emptyList())

        val result: ModelResult<List<StudentDomain>, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `cuando lista tiene elementos retorna SuccessResult con estudiantes`() = runTest {
        coEvery { preferenceUseCase.getIdCycleSchool() } returns 1
        val students = listOf(
            StudentDomain(1, "CURP", "2000-01-01", "5512345678", 10, "Juan", "Pérez", "López")
        )
        coEvery { studentRepository.getStudents(1) } returns SuccessResult(students)

        val result: ModelResult<List<StudentDomain>, ModelError> = useCase()

        assertTrue(result is SuccessResult)
        assertEquals(students, (result as SuccessResult).data)
        coVerify(exactly = 1) { studentRepository.getStudents(1) }
    }
}
