package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPartial
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.partial.GetListPartialRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.SuccessState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListPartialUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialUseCaseTest {

    private lateinit var getListPartialUseCase: GetListPartialUseCase
    private val getListPartialRepository: GetListPartialRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        getListPartialUseCase = GetListPartialUseCase(getListPartialRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de obtención de parciales exitoso.
     */
    @Test
    fun `invoke con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        val mockPartials = listOf(ResponseGetPartial(1, "Parcial 1", "2024-01-01", "2024-02-01", 101))
        coEvery { getListPartialRepository.executeGetListPartial(any()) } returns ResultSuccess(mockPartials)

        // Ejecutamos el caso de uso
        val result = getListPartialUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }

    /**
     * Test para el caso en que el repositorio devuelve una lista vacía.
     */
    @Test
    fun `invoke con lista vacia devuelve ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListPartialRepository.executeGetListPartial(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el caso de uso
        val result = getListPartialUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorState)
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `invoke con error del repositorio devuelve ErrorState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListPartialRepository.executeGetListPartial(any()) } returns ResultError(FailureService.ServerError)

        // Ejecutamos el caso de uso
        val result = getListPartialUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorState)
    }
}