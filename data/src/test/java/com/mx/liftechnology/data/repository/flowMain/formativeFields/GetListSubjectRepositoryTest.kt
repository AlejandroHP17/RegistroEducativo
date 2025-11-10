package com.mx.liftechnology.data.repository.flowMain.formativeFields

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.formativeField.GetListFormativeFieldsApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.formativeField.ResponseGetListFormativeFields
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
 * Tests para [GetListFormativeFieldsRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectRepositoryTest {

    private lateinit var getListFormativeFieldsRepository: GetListFormativeFieldsRepository
    private val getListFormativeFieldsApiCall: GetListFormativeFieldsApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetListFormativeFieldsRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        getListFormativeFieldsRepository = GetListFormativeFieldsRepositoryImpl(getListFormativeFieldsApiCall)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de la lista de materias.
     */
    @Test
    fun `executeGetListSubject con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<ResponseGetListFormativeFields?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<ResponseGetListFormativeFields?>?>> = Response.success(mockBody)

        coEvery { getListFormativeFieldsApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getListFormativeFieldsRepository.executeGetListFormativeFields(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultSuccess)
    }

    /**
     * Test para verificar el caso de error de la obtención de la lista de materias.
     */
    @Test
    fun `executeGetListSubject con respuesta de error`() = runBlocking {
        // Preparamos una respuesta de error mockeada
        val mockResponseBody: ResponseBody = mockk(relaxed = true)
        val mockResponse: Response<ResponseGeneric<List<ResponseGetListFormativeFields?>?>> = Response.error(400, mockResponseBody)

        coEvery { getListFormativeFieldsApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getListFormativeFieldsRepository.executeGetListFormativeFields(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}