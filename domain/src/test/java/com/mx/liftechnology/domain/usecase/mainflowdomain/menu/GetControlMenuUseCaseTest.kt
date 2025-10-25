package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.data.repository.flowMain.menu.MenuLocalRepository
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.SuccessResult
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetControlMenuUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios, como éxito y error.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetControlMenuUseCaseTest {

    private lateinit var getControlMenuUseCase: GetControlMenuUseCase
    private val localRepository: MenuLocalRepository = mockk()

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        getControlMenuUseCase = GetControlMenuUseCase(localRepository)
    }

    /**
     * Test para verificar que se devuelve [SuccessResult] cuando el repositorio retorna una lista no vacía.
     */
    @Test
    fun `invoke con lista no vacia devuelve SuccessState`() {
        // Preparamos el mock
        val menuList = listOf(ModelPrincipalMenuData("id", 1, "title"))
        every { localRepository.getControlMenu() } returns menuList

        // Ejecutamos el caso de uso
        val result = getControlMenuUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessResult)
    }

    /**
     * Test para verificar que se devuelve [ErrorResult] cuando el repositorio retorna una lista vacía.
     */
    @Test
    fun `invoke con lista vacia devuelve ErrorState`() {
        // Preparamos el mock
        every { localRepository.getControlMenu() } returns emptyList()

        // Ejecutamos el caso de uso
        val result = getControlMenuUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorResult)
    }

    /**
     * Test para verificar que se devuelve [ErrorResult] cuando el repositorio lanza una excepción.
     */
    @Test
    fun `invoke cuando el repositorio lanza excepcion devuelve ErrorState`() {
        // Preparamos el mock
        every { localRepository.getControlMenu() } throws RuntimeException("Error de base de datos")

        // Ejecutamos el caso de uso
        val result = getControlMenuUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorResult)
    }
}
