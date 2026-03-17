package com.mx.liftechnology.domain.usecase.student

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
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterStudentUseCase].
 */
class RegisterStudentUseCaseTest {

    private lateinit var studentRepository: StudentRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: RegisterStudentUseCase

    @Before
    fun setup() {
        studentRepository = mockk()
        preferenceUseCase = mockk()
        useCase = RegisterStudentUseCase(studentRepository, preferenceUseCase)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando faltan ids de profesor o ciclo`() = runTest {
        every { preferenceUseCase.getIdUser() } returns null
        every { preferenceUseCase.getIdCycleSchool() } returns null

        val result: ModelResult<StudentDomain?, ModelError> = useCase(
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "López",
            curp = "CURP",
            birthday = "2000-01-01",
            phoneNumber = "5512345678"
        )

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando datos son correctos delega en repositorio con valores normalizados`() = runTest {
        every { preferenceUseCase.getIdUser() } returns 10
        every { preferenceUseCase.getIdCycleSchool() } returns 20

        val domain = StudentDomain(1, "CURP", "2000-01-01", "5512345678", 10, "Juan", "Pérez", "López")
        coEvery {
            studentRepository.register(
                name = "Juan",
                lastName = "Pérez",
                secondLastName = "López",
                curp = "CURP",
                birthday = "2000-01-01",
                phoneNumber = "5512345678",
                teacherId = 10,
                schoolCycleId = 20,
                isActive = true
            )
        } returns SuccessResult(domain)

        val result: ModelResult<StudentDomain?, ModelError> = useCase(
            name = " Juan ",
            lastName = " Pérez ",
            secondLastName = " López ",
            curp = "CURP",
            birthday = "2000-01-01",
            phoneNumber = " 5512345678 "
        )

        assertTrue(result is SuccessResult)
        assertEquals(domain, (result as SuccessResult).data)

        coVerify(exactly = 1) {
            studentRepository.register(
                name = "Juan",
                lastName = "Pérez",
                secondLastName = "López",
                curp = "CURP",
                birthday = "2000-01-01",
                phoneNumber = "5512345678",
                teacherId = 10,
                schoolCycleId = 20,
                isActive = true
            )
        }
    }
}
