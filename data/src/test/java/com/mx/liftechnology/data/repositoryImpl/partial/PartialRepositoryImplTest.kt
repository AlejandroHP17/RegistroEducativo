package com.mx.liftechnology.data.repositoryImpl.partial

import com.mx.liftechnology.core.model.ResponseBasic
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.api.PartialApi
import com.mx.liftechnology.core.network.api.RequestPartials
import com.mx.liftechnology.core.network.api.RequestRegisterPartial
import com.mx.liftechnology.core.network.api.ResponseGetPartials
import com.mx.liftechnology.core.network.api.ResponseRegisterPartial
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
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
 * Tests para [PartialRepositoryImpl].
 * 
 * Verifica todos los escenarios posibles de las operaciones de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class PartialRepositoryImplTest {

    private lateinit var partialApi: PartialApi
    private lateinit var partialRepository: PartialRepositoryImpl

    @Before
    fun setup() {
        partialApi = mockk()
        partialRepository = PartialRepositoryImpl(partialApi)
    }

    // ========== Tests para getList ==========

    @Test
    fun `getList con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseList = listOf(
            ResponseGetPartials(
                partialId = 1,
                description = "Parcial 1",
                startDate = "2024-01-01",
                endDate = "2024-01-31"
            )
        )
        val responseGeneric = ResponseGeneric(
            data = responseList,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { partialApi.getListPartial(any()) } returns apiResponse

        // When
        val result = partialRepository.getList(schoolCycleId = 1)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.size)
        assertEquals("Parcial 1", successResult.data[0].description)
        assertEquals("2024-01-01", successResult.data[0].startDate)
        assertEquals("2024-01-31", successResult.data[0].endDate)
    }

    @Test
    fun `getList con lista vacía retorna SuccessResult con lista vacía`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric(
            data = emptyList<ResponseGetPartials>(),
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { partialApi.getListPartial(any()) } returns apiResponse

        // When
        val result = partialRepository.getList(schoolCycleId = 1)

        // Then
        assertTrue(result is SuccessResult)
        assertTrue((result as SuccessResult).data.isEmpty())
    }

    @Test
    fun `getList con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<List<ResponseGetPartials>>>(404, responseBody)

        coEvery { partialApi.getListPartial(any()) } returns response

        // When
        val result = partialRepository.getList(schoolCycleId = 999)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    // ========== Tests para register ==========

    @Test
    fun `register con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val adapterPeriods = listOf(
            DatePeriodDomain(
                position = 0,
                date = ModelStateOutFieldText(valueText = "2024-01-01 / 2024-01-31"),
                partialCycleGroup = 1
            ),
            DatePeriodDomain(
                position = 1,
                date = ModelStateOutFieldText(valueText = "2024-02-01 / 2024-02-28"),
                partialCycleGroup = 1
            )
        )
        val responseList = listOf(
            ResponseRegisterPartial(
                partialId = 1,
                description = "Parcial 1",
                startDate = "2024-01-01",
                endDate = "2024-01-31"
            ),
            ResponseRegisterPartial(
                partialId = 2,
                description = "Parcial 2",
                startDate = "2024-02-01",
                endDate = "2024-02-28"
            )
        )
        val responseGeneric = ResponseGeneric(
            data = responseList,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        val requestSlot = slot<RequestRegisterPartial>()
        coEvery { partialApi.registerListPartial(capture(requestSlot)) } returns apiResponse

        // When
        val result = partialRepository.register(
            adapterPeriods = adapterPeriods,
            cycleSchoolId = 1
        )

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(2, successResult.data.size)
        assertEquals("Parcial 1", successResult.data[0]?.description)
        assertEquals("2024-01-01", successResult.data[0]?.startDate)
        assertNotNull(requestSlot.captured)
        assertEquals(2, requestSlot.captured.listPartials.size)
        assertEquals("Parcial 1", requestSlot.captured.listPartials[0].description)
        assertEquals("2024-01-01", requestSlot.captured.listPartials[0].startDate)
        assertEquals("2024-01-31", requestSlot.captured.listPartials[0].endDate)
    }

    @Test
    fun `register parsea correctamente las fechas del formato date valueText`() = runTest {
        // Given
        val adapterPeriods = listOf(
            DatePeriodDomain(
                position = 0,
                date = ModelStateOutFieldText(valueText = "2024-01-01 / 2024-01-31"),
                partialCycleGroup = 1
            )
        )
        val responseList = listOf(
            ResponseRegisterPartial(
                partialId = 1,
                description = "Parcial 1",
                startDate = "2024-01-01",
                endDate = "2024-01-31"
            )
        )
        val responseGeneric = ResponseGeneric(
            data = responseList,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        var capturedRequest: RequestRegisterPartial? = null
        coEvery { partialApi.registerListPartial(capture(capturedRequest)) } returns apiResponse

        // When
        partialRepository.register(
            adapterPeriods = adapterPeriods,
            cycleSchoolId = 1
        )

        // Then
        assertNotNull(requestSlot.captured)
        assertEquals(1, requestSlot.captured.listPartials.size)
        assertEquals("2024-01-01", requestSlot.captured.listPartials[0].startDate)
        assertEquals("2024-01-31", requestSlot.captured.listPartials[0].endDate)
    }

    @Test
    fun `register con fecha mal formateada maneja el error correctamente`() = runTest {
        // Given
        val adapterPeriods = listOf(
            DatePeriodDomain(
                position = 0,
                date = ModelStateOutFieldText(valueText = "fecha-invalida"),
                partialCycleGroup = 1
            )
        )
        val responseList = listOf(
            ResponseRegisterPartial(
                partialId = 1,
                description = "Parcial 1",
                startDate = "",
                endDate = ""
            )
        )
        val responseGeneric = ResponseGeneric(
            data = responseList,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        val requestSlot = slot<RequestRegisterPartial>()
        coEvery { partialApi.registerListPartial(capture(requestSlot)) } returns apiResponse

        // When
        partialRepository.register(
            adapterPeriods = adapterPeriods,
            cycleSchoolId = 1
        )

        // Then
        assertNotNull(requestSlot.captured)
        assertEquals("", requestSlot.captured.listPartials[0].startDate)
        assertEquals("", requestSlot.captured.listPartials[0].endDate)
    }

    @Test
    fun `register con error 400 retorna ErrorResult BAD_REQUEST`() = runTest {
        // Given
        val adapterPeriods = listOf(
            DatePeriodDomain(
                position = 0,
                date = ModelStateOutFieldText(valueText = "2024-01-01 / 2024-01-31"),
                partialCycleGroup = 1
            )
        )
        val responseBody = "Bad Request".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<List<ResponseRegisterPartial>>>(400, responseBody)

        coEvery { partialApi.registerListPartial(any()) } returns response

        // When
        val result = partialRepository.register(
            adapterPeriods = adapterPeriods,
            cycleSchoolId = 1
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.BAD_REQUEST, (result as ErrorResult).error)
    }

    @Test
    fun `register con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        val adapterPeriods = listOf(
            DatePeriodDomain(
                position = 0,
                date = ModelStateOutFieldText(valueText = "2024-01-01 / 2024-01-31"),
                partialCycleGroup = 1
            )
        )

        coEvery { partialApi.registerListPartial(any()) } throws ConnectException("No hay conexión")

        // When
        val result = partialRepository.register(
            adapterPeriods = adapterPeriods,
            cycleSchoolId = 1
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }
}
