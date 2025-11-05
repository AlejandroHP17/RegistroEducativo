package com.mx.liftechnology.data.repository.flowMain.subject.assessment

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.GetListAssessmentTypeApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
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
 * Tests para [GetAssessmentTypeRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssessmentTypeRepositoryTest {

    private lateinit var getAssessmentTypeRepository: GetAssessmentTypeRepository
    private val getListAssessmentTypeApiCall: GetListAssessmentTypeApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetAssessmentTypeRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        getAssessmentTypeRepository = GetAssessmentTypeRepositoryImpl(getListAssessmentTypeApiCall)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de la lista de tipos de evaluación.
     */
    @Test
    fun `executeGetListAssessment con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<ResponseGetListAssessmentType?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<ResponseGetListAssessmentType?>?>> = Response.success(mockBody)

        coEvery { getListAssessmentTypeApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getAssessmentTypeRepository.executeGetListAssessment(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error de la obtención de la lista de tipos de evaluación.
     */
    @Test
    fun `executeGetListAssessment con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<ResponseGetListAssessmentType?>?>> = Response.error(400, mockResponseBody)

        coEvery { getListAssessmentTypeApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getAssessmentTypeRepository.executeGetListAssessment(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}