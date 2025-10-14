package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPartial
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.partial.GetListPartialRepository
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.SuccessState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListPartialUseCase].
 * Esta clase contiene los tests unitarios para el caso de uso que obtiene la lista de parciales.
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
     * Se ejecuta antes de cada test para inicializar el [GetListPartialUseCase] y sus dependencias.
     */
    @Before
    fun setUp() {
        getListPartialUseCase = GetListPartialUseCase(getListPartialRepository, preferenceUseCase)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de la lista de parciales.
     */
    @Test
    fun `invoke con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockPartial = ResponseGetPartial(1, "description", "2024-01-01", "2024-06-30", 1)
        coEvery { getListPartialRepository.executeGetListPartial(any()) } returns ResultSuccess(listOf(mockPartial))

        // Ejecutamos el método a probar
        val result = getListPartialUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessState)
    }
}