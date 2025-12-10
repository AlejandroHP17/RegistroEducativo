package com.mx.liftechnology.data.repositoryImpl.workType

import com.mx.liftechnology.core.model.ResponseBasic
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.api.ResponseGetListWorkType
import com.mx.liftechnology.core.network.api.ResponseGetWorkType
import com.mx.liftechnology.core.network.api.ResponseWorkTypeDetail
import com.mx.liftechnology.core.network.api.WorkTypeApi
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

/**
 * Tests para [WorkTypeRepositoryImpl].
 * 
 * Verifica todos los escenarios posibles de las operaciones de tipos de trabajo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class WorkTypeRepositoryImplTest {

    private lateinit var workTypeApi: WorkTypeApi
    private lateinit var workTypeRepository: WorkTypeRepositoryImpl

    @Before
    fun setup() {
        workTypeApi = mockk()
        workTypeRepository = WorkTypeRepositoryImpl(workTypeApi)
    }

    // ========== Tests para getWorkTypeByFormativeField ==========

    @Test
    fun `getWorkTypeByFormativeField con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val response = ResponseGetWorkType(
            formativeFieldName = "Matemáticas",
            formativeFieldId = 1,
            workTypes = listOf(
                ResponseWorkTypeDetail(
                    workTypeName = "Examen",
                    workTypeId = 1,
                    evaluationWeight = 50.0
                )
            )
        )
        val responseGeneric = ResponseGeneric(
            data = response,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { workTypeApi.getWorkTypeByFormativeField(any()) } returns apiResponse

        // When
        val result = workTypeRepository.getWorkTypeByFormativeField(formativeFieldId = 1)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertNotNull(successResult.data)
        assertEquals("Matemáticas", successResult.data?.formativeFieldName)
        assertEquals(1, successResult.data?.formativeFieldId)
        assertEquals(1, successResult.data?.workTypes?.size)
        assertEquals("Examen", successResult.data?.workTypes?.get(0)?.workTypeName)
    }

    @Test
    fun `getWorkTypeByFormativeField con respuesta null retorna ErrorResult EMPTY`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric<ResponseGetWorkType>(
            data = null,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { workTypeApi.getWorkTypeByFormativeField(any()) } returns apiResponse

        // When
        val result = workTypeRepository.getWorkTypeByFormativeField(formativeFieldId = 1)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `getWorkTypeByFormativeField con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseGetWorkType>>(404, responseBody)

        coEvery { workTypeApi.getWorkTypeByFormativeField(any()) } returns response

        // When
        val result = workTypeRepository.getWorkTypeByFormativeField(formativeFieldId = 999)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    // ========== Tests para getWorkTypeList ==========

    @Test
    fun `getWorkTypeList con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseList = listOf(
            ResponseGetListWorkType(
                workTypeId = 1,
                name = "Examen"
            ),
            ResponseGetListWorkType(
                workTypeId = 2,
                name = "Tarea"
            )
        )
        val responseGeneric = ResponseGeneric(
            data = responseList,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { workTypeApi.getListWorkType(any()) } returns apiResponse

        // When
        val result = workTypeRepository.getWorkTypeList(teacherId = 1)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(2, successResult.data.size)
        assertEquals(1, successResult.data[0].workTypeId)
        assertEquals("Examen", successResult.data[0].name)
        assertEquals(2, successResult.data[1].workTypeId)
        assertEquals("Tarea", successResult.data[1].name)
    }

    @Test
    fun `getWorkTypeList con lista vacía retorna SuccessResult con lista vacía`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric(
            data = emptyList<ResponseGetListWorkType>(),
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { workTypeApi.getListWorkType(any()) } returns apiResponse

        // When
        val result = workTypeRepository.getWorkTypeList(teacherId = 1)

        // Then
        assertTrue(result is SuccessResult)
        assertTrue((result as SuccessResult).data.isEmpty())
    }

    @Test
    fun `getWorkTypeList con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<List<ResponseGetListWorkType>>>(404, responseBody)

        coEvery { workTypeApi.getListWorkType(any()) } returns response

        // When
        val result = workTypeRepository.getWorkTypeList(teacherId = 999)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    @Test
    fun `getWorkTypeList con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { workTypeApi.getListWorkType(any()) } throws ConnectException("No hay conexión")

        // When
        val result = workTypeRepository.getWorkTypeList(teacherId = 1)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }
}
