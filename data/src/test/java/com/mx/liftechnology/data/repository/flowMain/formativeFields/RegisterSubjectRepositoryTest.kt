package com.mx.liftechnology.data.repository.flowMain.formativeFields

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.formativeField.RegisterFormativeFieldsBulkApiCall
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
 * Tests para [RegisterFormativeFieldsBulkRepository].
 * Esta clase contiene los tests unitarios para el repositorio que registra una nueva materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterSubjectRepositoryTest {

    private lateinit var registerFormativeFieldsBulkRepository: RegisterFormativeFieldsBulkRepository
    private val registerFormativeFieldsBulkApiCall: RegisterFormativeFieldsBulkApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [RegisterFormativeFieldsBulkRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        registerFormativeFieldsBulkRepository = RegisterFormativeFieldsBulkRepositoryImpl(registerFormativeFieldsBulkApiCall)
    }

    /**
     * Test para verificar el caso de éxito del registro de una materia.
     */
    @Test
    fun `executeRegisterOneSubject con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<String?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<String?>?>> = Response.success(mockBody)

        coEvery { registerFormativeFieldsBulkApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerFormativeFieldsBulkRepository.executeRegisterFormativeFieldsBulk(mockk())

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

        coEvery { registerFormativeFieldsBulkApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = registerFormativeFieldsBulkRepository.executeRegisterFormativeFieldsBulk(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}