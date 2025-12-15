package com.mx.liftechnology.domain.usecase.schoolCycle

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain
import com.mx.liftechnology.domain.repository.schoolCycle.SchoolCycleRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterCycleSchoolUseCase].
 */
class RegisterCycleSchoolUseCaseTest {

    private lateinit var schoolCycleRepository: SchoolCycleRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: RegisterCycleSchoolUseCase

    @Before
    fun setup() {
        schoolCycleRepository = mockk()
        preferenceUseCase = mockk()
        useCase = RegisterCycleSchoolUseCase(schoolCycleRepository, preferenceUseCase)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando faltan datos requeridos`() = runTest {
        io.mockk.every { preferenceUseCase.getIdUser() } returns null

        val result: ModelResult<RegisterSchoolCycleDomain, ModelError> = useCase(
            schoolId = 0,
            periodCatalogId = 0,
            cct = null,
            grade = 0,
            group = null,
            cycle = 0,
            shiftName = "",
            labelCycleState = ""
        )

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando registro es exitoso retorna SuccessResult`() = runTest {
        io.mockk.every { preferenceUseCase.getIdUser() } returns 10
        val domain = RegisterSchoolCycleDomain(cycleSchoolId = 1, name = "Ciclo", cct = "", grade = "", group = "", periodCatalogId = 1, shiftName = "")
        coEvery {
            schoolCycleRepository.registerCycleSchool(
                teacherId = 10,
                schoolId = 1,
                name = any(),
                cycleLabel = "2024-2025",
                grade = "1",
                nameGroup = "A",
                periodCatalogId = 1
            )
        } returns SuccessResult(domain)

        val result: ModelResult<RegisterSchoolCycleDomain, ModelError> = useCase(
            schoolId = 1,
            periodCatalogId = 1,
            cct = "CCT123",
            grade = 1,
            group = "A",
            cycle = 1,
            shiftName = "Matutino",
            labelCycleState = "2024-2025"
        )

        assertTrue(result is SuccessResult)
        assertEquals(domain, (result as SuccessResult).data)
    }

    @Test
    fun `cuando ocurre excepcion retorna UNKNOWN`() = runTest {
        io.mockk.every { preferenceUseCase.getIdUser() } returns 10
        coEvery { schoolCycleRepository.registerCycleSchool(any(), any(), any(), any(), any(), any(), any()) } throws RuntimeException("error")

        val result: ModelResult<RegisterSchoolCycleDomain, ModelError> = useCase(
            schoolId = 1,
            periodCatalogId = 1,
            cct = "CCT123",
            grade = 1,
            group = "A",
            cycle = 1,
            shiftName = "Matutino",
            labelCycleState = "2024-2025"
        )

        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNKNOWN, (result as ErrorResult).error)
    }
}
