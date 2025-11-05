package com.mx.liftechnology.data.repository.flowMain.subject

import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.apiCall.flowMain.GetListSubjectApiCall
import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListSubject
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
 * Tests para [GetListSubjectRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene la lista de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectRepositoryTest {

    private lateinit var getListSubjectRepository: GetListSubjectRepository
    private val getListSubjectApiCall: GetListSubjectApiCall = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetListSubjectRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        getListSubjectRepository = GetListSubjectRepositoryImpl(getListSubjectApiCall)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de la lista de materias.
     */
    @Test
    fun `executeGetListSubject con respuesta exitosa`() = runBlocking {
        // Preparamos una respuesta exitosa mockeada
        val mockBody: ResponseGeneric<List<ResponseGetListSubject?>?> = ResponseGeneric(emptyList(), mockk())
        val mockResponse: Response<ResponseGeneric<List<ResponseGetListSubject?>?>> = Response.success(mockBody)

        coEvery { getListSubjectApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getListSubjectRepository.executeGetListSubject(mockk())

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
        val mockResponse: Response<ResponseGeneric<List<ResponseGetListSubject?>?>> = Response.error(400, mockResponseBody)

        coEvery { getListSubjectApiCall.callApi(any()) } returns mockResponse

        // Ejecutamos el método a probar
        val result = getListSubjectRepository.executeGetListSubject(mockk())

        // Verificamos el resultado
        assertTrue(result is ResultError)
    }
}