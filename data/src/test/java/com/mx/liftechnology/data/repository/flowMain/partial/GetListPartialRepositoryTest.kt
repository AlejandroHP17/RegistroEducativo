package com.mx.liftechnology.data.repository.flowMain.partial

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.partial.GetListPartialApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPartial
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Tests para [GetListPartialRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene la lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialRepositoryTest {

    private lateinit var getListPartialRepository: GetListPartialRepository
    private val getListPartialApiCall: GetListPartialApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetListPartialRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        getListPartialRepository = GetListPartialRepositoryImpl(getListPartialApiCall)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de la lista de parciales.
     */
    @Test
    fun `executeGetListPartial con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<ResponseGetPartial?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<ResponseGetPartial?>?>> = Response.success(mockBody)

        coEvery { getListPartialApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getListPartialRepository.executeGetListPartial(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error de la obtención de la lista de parciales.
     */
    @Test
    fun `executeGetListPartial con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<ResponseGetPartial?>?>> = Response.error(400, mockResponseBody)

        coEvery { getListPartialApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getListPartialRepository.executeGetListPartial(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}