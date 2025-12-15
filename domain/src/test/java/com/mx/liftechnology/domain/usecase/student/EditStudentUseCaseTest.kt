package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.core.network.api.RequestEditStudent
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
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [EditStudentUseCase].
 */
class EditStudentUseCaseTest {

    private lateinit var studentRepository: StudentRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: EditStudentUseCase

    @Before
    fun setup() {
        studentRepository = mockk()
        preferenceUseCase = mockk()
        useCase = EditStudentUseCase(studentRepository, preferenceUseCase)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando faltan ids requeridos`() = runTest {
        every { preferenceUseCase.getIdUser() } returns null
        every { preferenceUseCase.getIdCycleSchool() } returns null

        val result: ModelResult<StudentDomain?, ModelError> = useCase(
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "López",
            curp = "CURP",
            birthday = "2000-01-01",
            phoneNumber = "5512345678",
            studentId = null
        )

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando datos son correctos construye RequestEditStudent y delega en repositorio`() = runTest {
        every { preferenceUseCase.getIdUser() } returns 10
        every { preferenceUseCase.getIdCycleSchool() } returns 20

        val domain = StudentDomain(1, "CURP", "2000-01-01", "5512345678", 10, "Juan", "Pérez", "López")
        val requestSlot = slot<RequestEditStudent>()
        coEvery { studentRepository.edit(capture(requestSlot), 1) } returns SuccessResult(domain)

        val result: ModelResult<StudentDomain?, ModelError> = useCase(
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "López",
            curp = "CURP",
            birthday = "2000-01-01",
            phoneNumber = "5512345678",
            studentId = 1
        )

        assertTrue(result is SuccessResult)
        assertEquals(domain, (result as SuccessResult).data)

        coVerify(exactly = 1) { studentRepository.edit(any(), 1) }
        val req = requestSlot.captured
        assertEquals("Juan", req.name)
        assertEquals("Pérez", req.lastName)
        assertEquals("López", req.secondLastName)
        assertEquals("CURP", req.curp)
        assertEquals("2000-01-01", req.birthday)
        assertEquals("5512345678", req.phoneNumber)
        assertEquals(10, req.teacherId)
        assertEquals(20, req.cycleSchoolId)
        assertTrue(req.isActive)
    }
}
