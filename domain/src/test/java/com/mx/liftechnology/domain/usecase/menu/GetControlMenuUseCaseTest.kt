package com.mx.liftechnology.domain.usecase.menu

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.PrincipalMenuDomain
import com.mx.liftechnology.domain.repository.menu.MenuLocalRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetControlMenuUseCase].
 */
class GetControlMenuUseCaseTest {

    private lateinit var localRepository: MenuLocalRepository
    private lateinit var useCase: GetControlMenuUseCase

    @Before
    fun setup() {
        localRepository = mockk()
        useCase = GetControlMenuUseCase(localRepository)
    }

    @Test
    fun `cuando lista esta vacia retorna ErrorResult EMPTY`() = runTest {
        coEvery { localRepository.getControlMenu() } returns SuccessResult(emptyList())

        val result: ModelResult<List<PrincipalMenuDomain>, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `cuando lista tiene elementos retorna SuccessResult`() = runTest {
        val list = listOf(PrincipalMenuDomain(id = 1, name = "Item", route = ""))
        coEvery { localRepository.getControlMenu() } returns SuccessResult(list)

        val result: ModelResult<List<PrincipalMenuDomain>, ModelError> = useCase()

        assertTrue(result is SuccessResult)
        assertEquals(list, (result as SuccessResult).data)
    }

    @Test
    fun `cuando repositorio retorna ErrorResult se mapea a CATCH`() = runTest {
        coEvery { localRepository.getControlMenu() } returns ErrorResult(mockk())

        val result: ModelResult<List<PrincipalMenuDomain>, ModelError> = useCase()

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.CATCH, (result as ErrorResult).error)
    }
}
