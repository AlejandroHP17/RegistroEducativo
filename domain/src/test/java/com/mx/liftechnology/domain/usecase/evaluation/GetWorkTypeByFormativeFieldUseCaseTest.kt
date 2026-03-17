package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.repository.workType.WorkTypeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest

/**
 * Tests para [GetWorkTypeByFormativeFieldUseCase].
 */
class GetWorkTypeByFormativeFieldUseCaseTest {

    private lateinit var workTypeRepository: WorkTypeRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: GetWorkTypeByFormativeFieldUseCase

    @Before
    fun setup() {
        workTypeRepository = mockk()
        preferenceUseCase = mockk()
        useCase = GetWorkTypeByFormativeFieldUseCase(workTypeRepository, preferenceUseCase)
    }

    @Test
    fun `retorna ErrorResult USER_INCOMPLETE_DATA cuando no hay campo formativo en preferencias`() = runTest {
        coEvery { preferenceUseCase.getIdFormativeField() } returns null

        val result: ModelResult<WorkTypeByFormativeFieldDomain, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando hay campo formativo delega en el repositorio`() = runTest {
        val formativeFieldId = 5
        val domain = WorkTypeByFormativeFieldDomain(emptyList())

        coEvery { preferenceUseCase.getIdFormativeField() } returns formativeFieldId
        coEvery { workTypeRepository.getWorkTypeByFormativeField(formativeFieldId) } returns SuccessResult(domain)

        val result: ModelResult<WorkTypeByFormativeFieldDomain, ModelError> = useCase()

        assertTrue(result is SuccessResult)
        assertEquals(domain, (result as SuccessResult).data)
        coVerify(exactly = 1) { workTypeRepository.getWorkTypeByFormativeField(formativeFieldId) }
    }
}
