package com.mx.liftechnology.domain.usecase.workType

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListWotyFofiUseCase].
 */
class GetListWotyFofiUseCaseTest {

    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var formativeFieldRepository: FormativeFieldRepository
    private lateinit var useCase: GetListWotyFofiUseCase

    @Before
    fun setup() {
        preferenceUseCase = mockk()
        formativeFieldRepository = mockk()
        useCase = GetListWotyFofiUseCase(preferenceUseCase, formativeFieldRepository)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando no hay ciclo escolar`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns null

        val result: ModelResult<WotyFofiDomain, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando hay ciclo escolar delega en el repositorio`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns 5
        val domain = WotyFofiDomain(emptyList())
        coEvery { formativeFieldRepository.getListWotyFofi(5) } returns SuccessResult(domain)

        val result: ModelResult<WotyFofiDomain, ModelError> = useCase()

        assertTrue(result is SuccessResult)
        assertEquals(domain, (result as SuccessResult).data)
    }
}
