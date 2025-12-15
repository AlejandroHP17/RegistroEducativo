package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import com.mx.liftechnology.domain.repository.workType.WorkTypeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListWorkTypeUseCase].
 */
class GetListWorkTypeUseCaseTest {

    private lateinit var workTypeRepository: WorkTypeRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: GetListWorkTypeUseCase

    @Before
    fun setup() {
        workTypeRepository = mockk()
        preferenceUseCase = mockk()
        useCase = GetListWorkTypeUseCase(workTypeRepository, preferenceUseCase)
    }

    @Test
    fun `retorna ErrorResult USER_INCOMPLETE_DATA cuando no hay id de usuario`() = runTest {
        io.mockk.every { preferenceUseCase.getIdUser() } returns null

        val result: ModelResult<List<WorkTypeDomain>, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando lista esta vacia retorna ErrorResult EMPTY`() = runTest {
        io.mockk.every { preferenceUseCase.getIdUser() } returns 1
        coEvery { workTypeRepository.getWorkTypeList(1) } returns SuccessResult(emptyList())

        val result: ModelResult<List<WorkTypeDomain>, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `cuando lista tiene elementos retorna SuccessResult`() = runTest {
        io.mockk.every { preferenceUseCase.getIdUser() } returns 1
        val list = listOf(WorkTypeDomain(id = 1, name = "Examen", isActive = true))
        coEvery { workTypeRepository.getWorkTypeList(1) } returns SuccessResult(list)

        val result: ModelResult<List<WorkTypeDomain>, ModelError> = useCase()

        assertTrue(result is SuccessResult)
        assertEquals(list, (result as SuccessResult).data)
        coVerify(exactly = 1) { workTypeRepository.getWorkTypeList(1) }
    }
}
