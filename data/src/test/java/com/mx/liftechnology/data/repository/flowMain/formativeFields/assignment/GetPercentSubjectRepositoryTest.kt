package com.mx.liftechnology.data.repository.flowMain.formativeFields.assignment

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.GetPercentSubjectIdApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPercentSubjectId
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
 * Tests para [GetPercentSubjectRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene el porcentaje de una materia.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetPercentSubjectRepositoryTest {

    private lateinit var getPercentSubjectRepository: GetPercentSubjectRepository
    private val getPercentSubjectIdApiCall: GetPercentSubjectIdApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetPercentSubjectRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        getPercentSubjectRepository = GetPercentSubjectRepositoryImpl(getPercentSubjectIdApiCall)
    }

    /**
     * Test para verificar el caso de éxito de la obtención del porcentaje de una materia.
     */
    @Test
    fun `executeGetPercentSubject con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<ResponseGetPercentSubjectId>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<ResponseGetPercentSubjectId>?>?> = Response.success(mockBody)

        coEvery { getPercentSubjectIdApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getPercentSubjectRepository.executeGetPercentSubject(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error de la obtención del porcentaje de una materia.
     */
    @Test
    fun `executeGetPercentSubject con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<ResponseGetPercentSubjectId>?>?> = Response.error(400, mockResponseBody)

        coEvery { getPercentSubjectIdApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getPercentSubjectRepository.executeGetPercentSubject(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}