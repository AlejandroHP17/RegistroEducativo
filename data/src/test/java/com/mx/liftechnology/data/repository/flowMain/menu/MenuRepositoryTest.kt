package com.mx.liftechnology.data.repository.flowMain.menu

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.GroupApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGroupTeacher
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
 * Tests para [MenuRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene los datos del menú.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuRepositoryTest {

    private lateinit var menuRepository: MenuRepository
    private val groupApiCall: GroupApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [MenuRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        menuRepository = MenuRepositoryImpl(groupApiCall)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de grupos.
     */
    @Test
    fun `executeGetGroup con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<ResponseGroupTeacher?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<ResponseGroupTeacher?>?>> = Response.success(mockBody)

        coEvery { groupApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = menuRepository.executeGetGroup(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de grupos.
     */
    @Test
    fun `executeGetGroup con respuesta exitosa con nulo de datos`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<ResponseGroupTeacher?>?> = ResponseGeneric(null, mockk())
        val mockResponse: Response<ResponseGeneric<List<ResponseGroupTeacher?>?>> = Response.success(mockBody)

        coEvery { groupApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = menuRepository.executeGetGroup(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de grupos.
     */
    @Test
    fun `executeGetGroup con respuesta exitosa con lista nula`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<ResponseGroupTeacher?>?> = ResponseGeneric(listOf(null), mockk())
        val mockResponse: Response<ResponseGeneric<List<ResponseGroupTeacher?>?>> = Response.success(mockBody)

        coEvery { groupApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = menuRepository.executeGetGroup(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }

    /**
     * Test para verificar el caso de error de la obtención de grupos.
     */
    @Test
    fun `executeGetGroup con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<ResponseGroupTeacher?>?>> = Response.error(400, mockResponseBody)

        coEvery { groupApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = menuRepository.executeGetGroup(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}