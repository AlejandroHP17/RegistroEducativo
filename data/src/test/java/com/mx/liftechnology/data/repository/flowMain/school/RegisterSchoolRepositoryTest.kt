package com.mx.liftechnology.data.repository.flowMain.school

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterCycleSchoolApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterCycleSchool
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
 * Tests para [RegisterCycleSchoolRepository].
 * Esta clase contiene los tests unitarios para el repositorio que registra una nueva escuela.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSchoolRepositoryTest {

    private lateinit var registerCycleSchoolRepository: RegisterCycleSchoolRepository
    private val registerCycleSchoolApiCall: RegisterCycleSchoolApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [RegisterCycleSchoolRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        registerCycleSchoolRepository = RegisterCycleSchoolRepositoryImpl(registerCycleSchoolApiCall)
    }

    /**
     * Test para verificar el caso de éxito del registro de una escuela.
     */
    @Test
    fun `executeRegisterOneSchool con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<String?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<String?>?>> = Response.success(mockBody)

        coEvery { registerCycleSchoolApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerCycleSchoolRepository.executeRegisterCycleSchool(RequestRegisterCycleSchool("", 1, 1, "", "", 1, 1, 1))

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error del registro de una escuela.
     */
    @Test
    fun `executeRegisterOneSchool con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<String?>?>> = Response.error(400, mockResponseBody)

        coEvery { registerCycleSchoolApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerCycleSchoolRepository.executeRegisterCycleSchool(RequestRegisterCycleSchool("", 1, 1, "", "", 1, 1, 1))

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}