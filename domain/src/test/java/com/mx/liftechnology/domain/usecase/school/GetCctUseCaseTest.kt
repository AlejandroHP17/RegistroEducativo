package com.mx.liftechnology.domain.usecase.school

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.registerschool.ResultSchoolDomain
import com.mx.liftechnology.domain.model.registerschool.SchoolSpinnerDomain
import com.mx.liftechnology.domain.model.schoolCycle.CCTDomain
import com.mx.liftechnology.domain.repository.school.SchoolRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetCctUseCase].
 */
class GetCctUseCaseTest {

    private lateinit var schoolRepository: SchoolRepository
    private lateinit var useCase: GetCctUseCase

    @Before
    fun setup() {
        schoolRepository = mockk()
        useCase = GetCctUseCase(schoolRepository)
    }

    @Test
    fun `cuando repositorio retorna SuccessResult se construye ResultSchoolDomain`() = runTest {
        val cctDomain = CCTDomain(
            cct = "ABC123",
            schoolName = "Escuela",
            schoolTypeId = 1,
            periodCatalog = emptyList()
        )
        coEvery { schoolRepository.getCct("ABC123") } returns SuccessResult(cctDomain)

        val result: ModelResult<ResultSchoolDomain, ModelError> = useCase("ABC123")

        assertTrue(result is SuccessResult)
        val data = (result as SuccessResult).data
        // Solo verificamos que los objetos principales no sean nulos y que contengan la data base
        assertTrue(data.spinner is SchoolSpinnerDomain)
        assertEquals(cctDomain, data.data)
    }

    @Test
    fun `cuando repositorio retorna ErrorResult se propaga el error`() = runTest {
        val error = ErrorResult<ModelError>(mockk())
        coEvery { schoolRepository.getCct("ABC123") } returns error

        val result: ModelResult<ResultSchoolDomain, ModelError> = useCase("ABC123")

        assertTrue(result is ErrorResult)
        assertEquals(error, result)
    }
}
