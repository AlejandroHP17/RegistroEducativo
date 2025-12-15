package com.mx.liftechnology.domain.usecase.menu

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.DialogStudentGroupDomain
import com.mx.liftechnology.domain.model.schoolCycle.InfoStudentGroupDomain
import com.mx.liftechnology.domain.model.schoolCycle.SchoolCycleDomain
import com.mx.liftechnology.domain.repository.schoolCycle.SchoolCycleRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetGroupMenuUseCase].
 */
class GetGroupMenuUseCaseTest {

    private lateinit var schoolCycleRepository: SchoolCycleRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: GetGroupMenuUseCase

    @Before
    fun setup() {
        schoolCycleRepository = mockk()
        preferenceUseCase = mockk(relaxed = true)
        useCase = GetGroupMenuUseCase(schoolCycleRepository, preferenceUseCase)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando no hay usuario`() = runTest {
        every { preferenceUseCase.getIdUser() } returns null

        val result: ModelResult<InfoStudentGroupDomain, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando repositorio retorna datos los convierte a InfoStudentGroupDomain`() = runTest {
        every { preferenceUseCase.getIdUser() } returns 5
        every { preferenceUseCase.getIdCycleSchool() } returns -1
        val cycles = listOf(
            SchoolCycleDomain(cycleSchoolId = 10, name = "", cct = "", grade = "", group = "", shiftName = "", periodCatalogId = 1)
        )
        coEvery { schoolCycleRepository.getCycleSchool(5) } returns SuccessResult(cycles)

        val result: ModelResult<InfoStudentGroupDomain, ModelError> = useCase()

        assertTrue(result is SuccessResult)
        val data = (result as SuccessResult).data
        assertEquals(1, data.listSchool.size)
        assertTrue(data.infoSchoolSelected is DialogStudentGroupDomain)
    }
}
