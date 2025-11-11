package com.mx.liftechnology.data.repository.flowMain.school

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.schoolCycle.GetCctApiCall
import com.mx.liftechnology.core.network.apiCall.schoolCycle.ResponseCctSchool
import com.mx.liftechnology.data.repository.schoolCycle.school.GetCctRepository
import com.mx.liftechnology.data.repository.schoolCycle.school.GetCctRepositoryImpl
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
 * Tests para [com.mx.liftechnology.data.repository.schoolCycle.school.GetCctRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene la información de una escuela a partir de su CCT.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetCctRepositoryTest {

    private lateinit var getCctRepository: GetCctRepository
    private val getCctApiCall: GetCctApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetCctRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        getCctRepository = GetCctRepositoryImpl(getCctApiCall)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de CCT.
     */
    @Test
    fun `executeGetCct con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<ResponseCctSchool?> = ResponseGeneric(mockk(), mockk())
        val mockResponse: Response<ResponseGeneric<ResponseCctSchool?>> = Response.success(mockBody)

        coEvery { getCctApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getCctRepository.executeGetCct("1234567890")

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error de la obtención de CCT.
     */
    @Test
    fun `executeGetCct con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<ResponseCctSchool?>> = Response.error(400, mockResponseBody)

        coEvery { getCctApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getCctRepository.executeGetCct("1234567890")

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}