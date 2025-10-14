package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.RegisterSubjectApiCall
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
 * Tests para [RegisterSubjectRepository].
 * Esta clase contiene los tests unitarios para el repositorio que registra una nueva materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSubjectRepositoryTest {

    private lateinit var registerSubjectRepository: RegisterSubjectRepository
    private val registerSubjectApiCall: RegisterSubjectApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [RegisterSubjectRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        registerSubjectRepository = RegisterSubjectRepositoryImp(registerSubjectApiCall)
    }

    /**
     * Test para verificar el caso de éxito del registro de una materia.
     */
    @Test
    fun `executeRegisterOneSubject con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<String?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<String?>?>> = Response.success(mockBody)

        coEvery { registerSubjectApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerSubjectRepository.executeRegisterOneSubject(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error del registro de una materia.
     */
    @Test
    fun `executeRegisterOneSubject con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<String?>?>> = Response.error(400, mockResponseBody)

        coEvery { registerSubjectApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerSubjectRepository.executeRegisterOneSubject(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}