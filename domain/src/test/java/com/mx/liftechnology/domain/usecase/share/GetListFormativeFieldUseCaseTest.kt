package com.mx.liftechnology.domain.usecase.share

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListFormativeFieldUseCase].
 */
class GetListFormativeFieldUseCaseTest {

    private lateinit var formativeFieldRepository: FormativeFieldRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: GetListFormativeFieldUseCase

    @Before
    fun setup() {
        formativeFieldRepository = mockk()
        preferenceUseCase = mockk()
        useCase = GetListFormativeFieldUseCase(formativeFieldRepository, preferenceUseCase)
    }

    @Test
    fun `retorna ErrorResult USER_INCOMPLETE_DATA cuando no hay ciclo escolar en preferencias`() = runTest {
        coEvery { preferenceUseCase.getIdCycleSchool() } returns null

        val result: ModelResult<List<FormativeFieldDomain>?, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando lista devuelta esta vacia retorna ErrorResult EMPTY`() = runTest {
        coEvery { preferenceUseCase.getIdCycleSchool() } returns 1
        coEvery { formativeFieldRepository.getList(1) } returns SuccessResult(emptyList<FormativeFieldDomain>())

        val result: ModelResult<List<FormativeFieldDomain>?, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `cuando lista tiene elementos retorna SuccessResult con materias`() = runTest {
        coEvery { preferenceUseCase.getIdCycleSchool() } returns 1
        val list = listOf(
            FormativeFieldDomain(id = 1, name = "Matemáticas", isActive = true)
        )
        coEvery { formativeFieldRepository.getList(1) } returns SuccessResult(list)

        val result: ModelResult<List<FormativeFieldDomain>?, ModelError> = useCase()

        assertTrue(result is SuccessResult)
        assertEquals(list, (result as SuccessResult).data)
        coVerify(exactly = 1) { formativeFieldRepository.getList(1) }
    }
}
