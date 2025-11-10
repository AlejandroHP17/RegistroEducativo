package com.mx.liftechnology.data.repository.flowMain.formativeFields.evaluationtype

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.GetListEvaluationTypeApiCall
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
 * Tests para [GetListEvaluationTypeRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListEvaluationTypeRepositoryTest {

    private lateinit var getListEvaluationTypeRepository: GetListEvaluationTypeRepository
    private val getListEvaluationTypeApiCall: GetListEvaluationTypeApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetListEvaluationTypeRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        getListEvaluationTypeRepository = GetListEvaluationTypeRepositoryImpl(getListEvaluationTypeApiCall)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de la lista de tipos de evaluación.
     */
    @Test
    fun `executeGetListEvaluationType con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<String>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<String>?>?> = Response.success(mockBody)

        coEvery { getListEvaluationTypeApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getListEvaluationTypeRepository.executeGetListEvaluationType(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error de la obtención de la lista de tipos de evaluación.
     */
    @Test
    fun `executeGetListEvaluationType con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<String>?>?> = Response.error(400, mockResponseBody)

        coEvery { getListEvaluationTypeApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getListEvaluationTypeRepository.executeGetListEvaluationType(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}