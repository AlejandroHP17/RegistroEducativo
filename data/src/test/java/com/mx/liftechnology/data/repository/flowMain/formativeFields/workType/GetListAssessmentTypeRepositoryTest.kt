package com.mx.liftechnology.data.repository.flowMain.formativeFields.workType

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.formativeField.GetListWorkTypeApiCall
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseGetListWorkType
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeRepository
import com.mx.liftechnology.data.repository.formativeField.GetWorkTypeRepositoryImpl
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
 * Tests para [com.mx.liftechnology.data.repository.formativeField.GetWorkTypeRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListAssessmentTypeRepositoryTest {

    private lateinit var getWorkTypeRepository: GetWorkTypeRepository
    private val getListWorkTypeApiCall: GetListWorkTypeApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetWorkTypeRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        getWorkTypeRepository = GetWorkTypeRepositoryImpl(getListWorkTypeApiCall)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de la lista de tipos de evaluación.
     */
    @Test
    fun `executeGetListAssessment con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<ResponseGetListWorkType?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<ResponseGetListWorkType?>?>> = Response.success(mockBody)

        coEvery { getListWorkTypeApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getWorkTypeRepository.executeGetListWorkType(mockk())

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
        val mockResponse: Response<ResponseGeneric<List<ResponseGetListWorkType?>?>> = Response.error(400, mockResponseBody)

        coEvery { getListWorkTypeApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getWorkTypeRepository.executeGetListWorkType(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}