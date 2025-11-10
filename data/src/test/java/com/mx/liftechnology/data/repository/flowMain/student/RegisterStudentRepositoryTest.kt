package com.mx.liftechnology.data.repository.flowMain.student

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.student.RegisterStudentApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.student.RequestRegisterStudent
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
 * Tests para [RegisterStudentRepository].
 * Esta clase contiene los tests unitarios para el repositorio que registra un nuevo estudiante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterStudentRepositoryTest {

    private lateinit var registerStudentRepository: RegisterStudentRepository
    private val registerStudentApiCall: RegisterStudentApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [RegisterStudentRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        registerStudentRepository = RegisterStudentRepositoryImpl(registerStudentApiCall)
    }

    /**
     * Test para verificar el caso de éxito del registro de un estudiante.
     */
    @Test
    fun `executeRegisterOneStudent con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<String?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<String?>?>> = Response.success(mockBody)

        coEvery { registerStudentApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerStudentRepository.executeRegisterOneStudent(RequestRegisterStudent("", "", "", "", "", "", 1, 1, 1))

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error del registro de un estudiante.
     */
    @Test
    fun `executeRegisterOneStudent con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<String?>?>> = Response.error(400, mockResponseBody)

        coEvery { registerStudentApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerStudentRepository.executeRegisterOneStudent(RequestRegisterStudent("", "", "", "", "", "", 1, 1, 1))

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}