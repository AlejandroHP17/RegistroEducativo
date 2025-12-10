package com.mx.liftechnology.data.repositoryImpl.schoolCycle

import com.mx.liftechnology.core.model.ResponseBasic
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.api.RequestRegisterSchoolCycle
import com.mx.liftechnology.core.network.api.ResponseGroupTeacher
import com.mx.liftechnology.core.network.api.ResponseRegisterSchoolCycle
import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

/**
 * Tests para [SchoolCycleRepositoryImpl].
 * 
 * Verifica todos los escenarios posibles de las operaciones de ciclos escolares.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SchoolCycleRepositoryImplTest {

    private lateinit var schoolCycleApi: SchoolCycleApi
    private lateinit var schoolCycleRepository: SchoolCycleRepositoryImpl

    @Before
    fun setup() {
        schoolCycleApi = mockk()
        schoolCycleRepository = SchoolCycleRepositoryImpl(schoolCycleApi)
    }

    // ========== Tests para getCycleSchool ==========

    @Test
    fun `getCycleSchool con respuesta exitosa y lista no vacía retorna SuccessResult`() = runTest {
        // Given
        val responseList = listOf(
            ResponseGroupTeacher(
                teacherId = 1,
                schoolId = 10,
                name = "2024-2025",
                grade = "1°",
                groupName = "A",
                isActive = true,
                schoolCycleId = 100
            )
        )
        val responseGeneric = ResponseGeneric(
            data = responseList,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { schoolCycleApi.getGroup(any()) } returns apiResponse

        // When
        val result = schoolCycleRepository.getCycleSchool(teacherId = 1)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.size)
        assertEquals("2024-2025", successResult.data[0].name)
        assertEquals("1°", successResult.data[0].grade)
        assertEquals("A", successResult.data[0].group)
    }

    @Test
    fun `getCycleSchool con lista vacía retorna ErrorResult EMPTY`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric(
            data = emptyList<ResponseGroupTeacher>(),
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { schoolCycleApi.getGroup(any()) } returns apiResponse

        // When
        val result = schoolCycleRepository.getCycleSchool(teacherId = 1)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.EMPTY, (result as ErrorResult).error)
    }

    @Test
    fun `getCycleSchool con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<List<ResponseGroupTeacher>>>(404, responseBody)

        coEvery { schoolCycleApi.getGroup(any()) } returns response

        // When
        val result = schoolCycleRepository.getCycleSchool(teacherId = 999)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    @Test
    fun `getCycleSchool con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { schoolCycleApi.getGroup(any()) } throws ConnectException("No hay conexión")

        // When
        val result = schoolCycleRepository.getCycleSchool(teacherId = 1)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }

    // ========== Tests para registerCycleSchool ==========

    @Test
    fun `registerCycleSchool con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val response = ResponseRegisterSchoolCycle(
            teacherId = 1,
            schoolId = 10,
            name = "2024-2025",
            isActive = true,
            schoolCycleId = 100
        )
        val responseGeneric = ResponseGeneric(
            data = response,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        var capturedRequest: RequestRegisterSchoolCycle? = null
        coEvery { schoolCycleApi.registerSchoolCycle(capture(capturedRequest)) } returns apiResponse

        // When
        val result = schoolCycleRepository.registerCycleSchool(
            teacherId = 1,
            schoolId = 10,
            name = "2024-2025",
            cycleLabel = "Ciclo 1",
            grade = "1°",
            nameGroup = "A",
            periodCatalogId = 1
        )

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.teacherId)
        assertEquals(10, successResult.data.schoolId)
        assertEquals("2024-2025", successResult.data.name)
        assertEquals(true, successResult.data.isActive)
        assertEquals(100, successResult.data.idCycleSchool)
        assertNotNull(requestSlot.captured)
        assertEquals(true, requestSlot.captured.isActive) // Siempre debe ser true según el código
        assertEquals("", requestSlot.captured.cycleLabel) // Siempre debe ser "" según el código
    }

    @Test
    fun `registerCycleSchool siempre establece isActive en true y cycleLabel en vacío`() = runTest {
        // Given
        val response = ResponseRegisterSchoolCycle(
            teacherId = 1,
            schoolId = 10,
            name = "2024-2025",
            isActive = true,
            schoolCycleId = 100
        )
        val responseGeneric = ResponseGeneric(
            data = response,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        val requestSlot = slot<RequestRegisterSchoolCycle>()
        coEvery { schoolCycleApi.registerSchoolCycle(capture(requestSlot)) } returns apiResponse

        // When
        schoolCycleRepository.registerCycleSchool(
            teacherId = 1,
            schoolId = 10,
            name = "2024-2025",
            cycleLabel = "Ciclo 1", // Se pasa un valor pero debe ser ""
            grade = "1°",
            nameGroup = "A",
            periodCatalogId = 1
        )

        // Then
        assertNotNull(requestSlot.captured)
        assertEquals(true, requestSlot.captured.isActive)
        assertEquals("", requestSlot.captured.cycleLabel)
    }

    @Test
    fun `registerCycleSchool con error 409 retorna ErrorResult CONFLICT`() = runTest {
        // Given
        val responseBody = "Conflict".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseRegisterSchoolCycle>>(409, responseBody)

        coEvery { schoolCycleApi.registerSchoolCycle(any()) } returns response

        // When
        val result = schoolCycleRepository.registerCycleSchool(
            teacherId = 1,
            schoolId = 10,
            name = "2024-2025",
            cycleLabel = "",
            grade = "1°",
            nameGroup = "A",
            periodCatalogId = 1
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.CONFLICT, (result as ErrorResult).error)
    }

    @Test
    fun `registerCycleSchool con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { schoolCycleApi.registerSchoolCycle(any()) } throws ConnectException("No hay conexión")

        // When
        val result = schoolCycleRepository.registerCycleSchool(
            teacherId = 1,
            schoolId = 10,
            name = "2024-2025",
            cycleLabel = "",
            grade = "1°",
            nameGroup = "A",
            periodCatalogId = 1
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }
}
